package dev.kreienbuehl.mapcompose.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcompose.core.ColorFilterProvider
import dev.kreienbuehl.mapcompose.core.Tile
import dev.kreienbuehl.mapcompose.core.VisibleTilesResolver
import dev.kreienbuehl.mapcompose.ui.state.ZoomPanRotateState

@Composable
expect internal fun TileCanvas(
    modifier: Modifier,
    zoomPRState: ZoomPanRotateState,
    visibleTilesResolver: VisibleTilesResolver,
    tileSize: Int,
    alphaTick: Float,
    colorFilterProvider: ColorFilterProvider?,
    tilesToRender: List<Tile>,
    isFilteringBitmap: () -> Boolean,
): Unit

/*


@Composable
internal fun TileCanvas(
    modifier: Modifier,
    zoomPRState: ZoomPanRotateState,
    visibleTilesResolver: VisibleTilesResolver,
    tileSize: Int,
    alphaTick: Float,
    colorFilterProvider: ColorFilterProvider?,
    tilesToRender: List<Tile>,
    isFilteringBitmap: () -> Boolean,
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
                    it.nativeCanvas.drawImage(Image.makeFromBitmap(tile.bitmap), l.toFloat(), t.toFloat())
                }

                /* If a tile isn't fully opaque, increase its alpha state by the alpha tick */
                if (tile.alpha < 1f) {
                    tile.alpha = (tile.alpha + alphaTick).coerceAtMost(1f)
                }
            }
        }
    }
}

*/