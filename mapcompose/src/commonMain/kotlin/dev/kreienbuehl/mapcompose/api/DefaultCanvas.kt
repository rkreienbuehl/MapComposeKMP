package dev.kreienbuehl.mapcompose.api

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcompose.ui.state.MapState

/**
 * A custom canvas which moves, scales, and rotates along with the map (exactly like some internal
 * components of [MapUI]).
 * It's an example which can be used in a custom composable (as it takes a [drawBlock] as input).
 *
 * This implementation only uses the public API. Therefore, different implementations are possible.
 * However, this implementation should fit most needs for custom drawings inside [MapUI].
 */
@Composable
fun DefaultCanvas(
    modifier: Modifier,
    mapState: MapState,
    drawBlock: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
    ) {
        withTransform({
            /* Geometric transformations seem to be applied in reversed order of declaration */
            translate(left = -mapState.scroll.x, top = -mapState.scroll.y)
            rotate(
                degrees = mapState.rotation,
                pivot = Offset(
                    x = mapState.centroidX.toFloat() * mapState.fullSize.width * mapState.scale,
                    y = mapState.centroidY.toFloat() * mapState.fullSize.height * mapState.scale
                )
            )
            scale(scale = mapState.scale, Offset.Zero)
        }, drawBlock)
    }
}