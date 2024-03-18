package dev.kreienbuehl.mapcomposekmp.viewmodels

import androidx.lifecycle.ViewModel
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.scale
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcompose.core.TileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Shows how MapCompose behaves with remote HTTP tiles.
 */
class HttpTilesVM : ViewModel() {
    private val tileStreamProvider = makeTileStreamProvider()

    val state = MapState(
        levelCount = 4,
        fullWidth = 4096,
        fullHeight = 4096,
        workerCount = 16  // Notice how we increase the worker count when performing HTTP requests
    ).apply {
        addLayer(tileStreamProvider)
        scale = 0f
        shouldLoopScale = true
    }
}

/**
 * A [TileStreamProvider] which performs HTTP requests.
 */
private fun makeTileStreamProvider() =
    TileStreamProvider { row, col, zoomLvl ->
        try {
            val url =
                URL("https://raw.githubusercontent.com/p-lr/MapCompose/master/demo/src/main/assets/tiles/mont_blanc/$zoomLvl/$row/$col.jpg")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            BufferedInputStream(connection.inputStream).readBytes()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }