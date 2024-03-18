package dev.kreienbuehl.mapcompose.core

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import dev.kreienbuehl.mapcompose.utils.toArray
import dev.kreienbuehl.mapcompose.utils.toImage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.selects.select
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image


/**
 * The engine of MapCompose. The view-model uses two channels to communicate with the [TileCollector]:
 * * one to send [TileSpec]s (a [SendChannel])
 * * one to receive [TileSpec]s (a [ReceiveChannel])
 *
 * The [TileCollector] encapsulates all the complexity that transforms a [TileSpec] into a [Tile].
 * ```
 *                                              _____________________________________________________________________
 *                                             |                           TileCollector             ____________    |
 *                                  tiles      |                                                    |  ________  |   |
 *              ---------------- [*********] <----------------------------------------------------- | | worker | |   |
 *             |                               |                                                    |  --------  |   |
 *             ↓                               |                                                    |  ________  |   |
 *  _____________________                      |                                   tileSpecs        | | worker | |   |
 * | TileCanvasViewModel |                     |    _____________________  <---- [**********] <---- |  --------  |   |
 *  ---------------------  ----> [*********] ----> | tileCollectorKernel |                          |  ________  |   |
 *                                tileSpecs    |    ---------------------  ----> [**********] ----> | | worker | |   |
 *                                             |                                   tileSpecs        |  --------  |   |
 *                                             |                                                    |____________|   |
 *                                             |                                                      worker pool    |
 *                                             |                                                                     |
 *                                              ---------------------------------------------------------------------
 * ```
 * This architecture is an example of Communicating Sequential Processes (CSP).
 *
 * @author p-lr on 22/06/19
 */

internal class TileCollector(
    private val workerCount: Int,
    private val bitmapConfig: Unit,
    private val tileSize: Int
) {

    /**
     * Sets up the tile collector machinery. The architecture is inspired from
     * [Kotlin Conf 2018](https://www.youtube.com/watch?v=a3agLJQ6vt8).
     * It support back-pressure, and avoids deadlock in CSP taking into account recommendations of
     * this [article](https://medium.com/@elizarov/deadlocks-in-non-hierarchical-csp-e5910d137cc),
     * which is from the same author.
     *
     * @param [tileSpecs] channel of [TileSpec], which capacity should be [Channel.RENDEZVOUS].
     * @param [tilesOutput] channel of [Tile], which should be set as [Channel.RENDEZVOUS].
     */
    suspend fun collectTiles(
        tileSpecs: ReceiveChannel<TileSpec>,
        tilesOutput: SendChannel<Tile>,
        layers: List<Layer>,
        bitmapFlow: Flow<ByteArray>,
    ) = coroutineScope {
        val tilesToDownload = Channel<TileSpec>(capacity = Channel.RENDEZVOUS)
        val tilesDownloadedFromWorker = Channel<TileSpec>(capacity = 1)

        repeat(workerCount) {
            worker(
                tilesToDownload,
                tilesDownloadedFromWorker,
                tilesOutput,
                layers,
                bitmapFlow
            )
        }
        tileCollectorKernel(tileSpecs, tilesToDownload, tilesDownloadedFromWorker)
    }

    private fun CoroutineScope.worker(
        tilesToDownload: ReceiveChannel<TileSpec>,
        tilesDownloaded: SendChannel<TileSpec>,
        tilesOutput: SendChannel<Tile>,
        layers: List<Layer>,
        bitmapFlow: Flow<ByteArray>
    ) = launch(dispatcher) {

        val layerIds = layers.map { it.id }
//        val bitmapLoadingOptionsForLayer = layerIds.associateWith {
//            BitmapFactory.Options().apply {
//                inPreferredConfig = bitmapConfig
//            }
//        }
//        val bitmapForLayer = layerIds.associateWith {
//            Bitmap.createBitmap(tileSize, tileSize, bitmapConfig)
//        }
//
        val paint = Paint(/*Paint.FILTER_BITMAP_FLAG*/)

        suspend fun getBitmap(
            spec: TileSpec,
            layer: Layer,
            inBitmapForced: ByteArray? = null
        ): BitmapForLayer {
//            val bitmapLoadingOptions = bitmapLoadingOptionsForLayer[layer.id] ?: return BitmapForLayer(null, layer)
//
//            bitmapLoadingOptions.inMutable = true
//            bitmapLoadingOptions.inBitmap = inBitmapForced ?: bitmapForLayer[layer.id]
//            bitmapLoadingOptions.inSampleSize = spec.subSample

            val i = layer.tileStreamProvider.getTileStream(spec.row, spec.col, spec.zoom)

            return i.run {
//                val bitmap = runCatching {
//                    this?.let { it.toImage() }
////                    BitmapFactory.decodeStream(i, null, bitmapLoadingOptions)
//                }.getOrNull()
                BitmapForLayer(this, layer)
            }
        }

        for (spec in tilesToDownload) {
            if (layers.isEmpty()) {
                tilesDownloaded.send(spec)
                continue
            }

            val bitmapForLayers = layers.mapIndexed { index, layer ->
                async {
                    /* Attempt to reuse an existing bitmap for the first layer */
                    getBitmap(spec, layer, if (index == 0) bitmapFlow.single() else null)
                }
            }.awaitAll()

            val resultBitmap = bitmapForLayers.firstOrNull()?.bitmap ?: run {
                /* If the decoding of the first layer failed, skip the rest */
                tilesDownloaded.send(spec)
                null
            } ?: continue

            val canvas = Canvas(resultBitmap.toImage())

            for (result in bitmapForLayers.drop(1)) {
                paint.alpha = result.layer.alpha
                if (result.bitmap == null) continue
                canvas.drawImage(result.bitmap.toImage(), Offset.Zero, paint)
//                canvas.nativeCanvas.drawImage(Image.makeFromBitmap(result.bitmap), 0f, 0f)
            }

            val tile = Tile(
                spec.zoom,
                spec.row,
                spec.col,
                spec.subSample,
                layerIds,
                layers.map { it.alpha }).apply {
                this.bitmap = resultBitmap
            }
            tilesOutput.send(tile)
            tilesDownloaded.send(spec)
        }
    }

    private fun CoroutineScope.tileCollectorKernel(
        tileSpecs: ReceiveChannel<TileSpec>,
        tilesToDownload: SendChannel<TileSpec>,
        tilesDownloadedFromWorker: ReceiveChannel<TileSpec>,
    ) = launch(Dispatchers.Default) {

        val specsBeingProcessed = mutableListOf<TileSpec>()

        while (true) {
            select<Unit> {
                tilesDownloadedFromWorker.onReceive {
                    specsBeingProcessed.remove(it)
                }
                tileSpecs.onReceive {
                    if (it !in specsBeingProcessed) {
                        /* Add it to the list of locations being processed */
                        specsBeingProcessed.add(it)

                        /* Now download the tile */
                        tilesToDownload.send(it)
                    }
                }
            }
        }
    }

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks.
     */
    fun shutdownNow() {
        // executor.shutdownNow()
    }

    /**
     * When using a [LinkedBlockingQueue], the core pool size mustn't be 0, or the active thread
     * count won't be greater than 1. Previous versions used a [SynchronousQueue], which could have
     * a core pool size of 0 and a growing count of active threads. However, a [Runnable] could be
     * rejected when no thread were available. Starting from kotlinx.coroutines 1.4.0, this cause
     * the associated coroutine to be cancelled. By using a [LinkedBlockingQueue], we avoid rejections.
     */
//    private val executor = ThreadPoolExecutor(
//        workerCount, workerCount,
//        60L, TimeUnit.SECONDS, LinkedBlockingQueue()
//    ).apply {
//        allowCoreThreadTimeOut(true)
//    }
    private val backgroundDispatcher = newFixedThreadPoolContext(workerCount, "TileCollectorPool")
    private val dispatcher = backgroundDispatcher.limitedParallelism(workerCount) //executor.asCoroutineDispatcher()
}

private data class BitmapForLayer(val bitmap: ByteArray?, val layer: Layer) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BitmapForLayer

        if (bitmap != null) {
            if (other.bitmap == null) return false
            if (!bitmap.contentEquals(other.bitmap)) return false
        } else if (other.bitmap != null) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bitmap?.contentHashCode() ?: 0
        result = 31 * result + layer.hashCode()
        return result
    }
}