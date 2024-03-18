package dev.kreienbuehl.mapcompose.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import dev.kreienbuehl.mapcompose.core.ColorFilterProvider
import dev.kreienbuehl.mapcompose.core.Tile
import dev.kreienbuehl.mapcompose.core.VisibleTilesResolver
import dev.kreienbuehl.mapcompose.ui.state.ZoomPanRotateState
import org.jetbrains.skia.Image

@Composable
internal actual fun TileCanvas(
    modifier: Modifier,
    zoomPRState: ZoomPanRotateState,
    visibleTilesResolver: VisibleTilesResolver,
    tileSize: Int,
    alphaTick: Float,
    colorFilterProvider: ColorFilterProvider?,
    tilesToRender: List<Tile>,
    isFilteringBitmap: () -> Boolean
) {
    val paint: Paint = remember {
        Paint().apply {
            isAntiAlias = false
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        withTransform({
            /* Geometric transformations seem to be applied in reversed order of declaration */
            rotate(
                degrees = zoomPRState.rotation,
                pivot = Offset(
                    x = zoomPRState.pivotX.toFloat(),
                    y = zoomPRState.pivotY.toFloat()
                )
            )
            translate(left = -zoomPRState.scrollX, top = -zoomPRState.scrollY)
            scale(scale = zoomPRState.scale, Offset.Zero)
        }) {
//            paint.isFilterBitmap = isFilteringBitmap() TODO

            for (tile in tilesToRender) {
                val scaleForLevel = visibleTilesResolver.getScaleForLevel(tile.zoom)
                    ?: continue
                val tileScaled = (tileSize / scaleForLevel).toInt()
                val l = tile.col * tileScaled
                val t = tile.row * tileScaled

                val colorFilter = colorFilterProvider?.getColorFilter(tile.row, tile.col, tile.zoom)

                paint.alpha = tile.alpha
                paint.colorFilter = colorFilter

                drawIntoCanvas {
//                    it.nativeCanvas.drawImage(tile.bitmap.toBufferedImage().toImage(), l.toFloat(), t.toFloat())
                    it.nativeCanvas.drawImage(Image.makeFromEncoded(tile.bitmap), l.toFloat(), t.toFloat())
                }

                /* If a tile isn't fully opaque, increase its alpha state by the alpha tick */
                if (tile.alpha < 1f) {
                    tile.alpha = (tile.alpha + alphaTick).coerceAtMost(1f)
                }
            }
        }
    }
}