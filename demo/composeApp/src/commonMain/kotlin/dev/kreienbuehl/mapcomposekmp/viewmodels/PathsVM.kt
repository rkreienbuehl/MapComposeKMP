package dev.kreienbuehl.mapcomposekmp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addCallout
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addPath
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.onPathClick
import dev.kreienbuehl.mapcompose.api.onPathLongPress
import dev.kreienbuehl.mapcompose.api.scrollTo
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcomposekmp.ui.widgets.Callout
import dev.kreienbuehl.mapcompose.ui.paths.PathDataBuilder
import dev.kreienbuehl.mapcompose.ui.paths.model.PatternItem
import dev.kreienbuehl.mapcompose.ui.paths.model.PatternItem.Dash
import dev.kreienbuehl.mapcompose.ui.paths.model.PatternItem.Gap
import dev.kreienbuehl.mapcompose.ui.state.MapState
import dev.kreienbuehl.mapcompose.utils.dpToPx
import mapcomposekmp.demo.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * In this sample, we add "tracks" to the map. The tracks are rendered as paths using MapCompose.
 */
class PathsVM() : ScreenModel {
    private val tileStreamProvider = makeTileStreamProvider()

    val state = MapState(4, 4096, 4096).apply {
        addLayer(tileStreamProvider)
        shouldLoopScale = true
        enableRotation()

        /**
         * Demonstrates path click.
         */
        onPathClick { id, x, y ->
            var shouldAnimate by mutableStateOf(true)
            addCallout(
                id, x, y,
                absoluteOffset = Offset(0f, -20f),
            ) {
                Callout(x, y, title = "Click on $id", shouldAnimate) {
                    shouldAnimate = false
                }
            }
        }

        /**
         * Demonstrates path long-press.
         */
        onPathLongPress { id, x, y ->
            var shouldAnimate by mutableStateOf(true)
            addCallout(
                id, x, y,
                absoluteOffset = Offset(0f, -20f),
            ) {
                Callout(x, y, title = "Long-press on $id", shouldAnimate) {
                    shouldAnimate = false
                }
            }
        }

        screenModelScope.launch {
            scrollTo(0.72, 0.3)
        }
    }


    init {
        /* Add tracks */
        addTrack("track1", Color(0xFF448AFF))
        addTrack("track2", Color(0xFFFFFF00))
        addTrack("track3", pattern = listOf(Dash(dpToPx(8f)), Gap(dpToPx(4f))))
    }

    /**
     * In this sample, we retrieve track points from text files in the assets.
     * To add a path, use the [addPath] api. From inside the builder block, you can add individual
     * points or a list of points.
     * Here, since we're getting points from a sequence, we add them on the fly using [PathDataBuilder.addPoint].
     */
    @OptIn(ExperimentalResourceApi::class)
    private fun addTrack(
        trackName: String,
        color: Color? = null,
        pattern: List<PatternItem>? = null,
        clickable: Boolean = true
    ) {
        with(state) {
            var lines: Sequence<String>? = null

            screenModelScope.launch {
                Res.readBytes("files/tracks/$trackName.txt").decodeToString().lineSequence()
            }

            if (lines == null) {
                return
            }

            addPath(
                id = trackName, color = color, clickable = true, pattern = pattern
            ) {
                for (line in lines) {
                    val values = line.split(',').map(String::toDouble)
                    addPoint(values[0], values[1])
                }
            }
        }
    }
}