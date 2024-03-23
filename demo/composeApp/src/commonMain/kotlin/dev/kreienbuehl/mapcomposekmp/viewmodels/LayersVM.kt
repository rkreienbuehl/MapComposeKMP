package dev.kreienbuehl.mapcomposekmp.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.scrollTo
import dev.kreienbuehl.mapcompose.api.setLayerOpacity
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcompose.core.TileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import mapcomposekmp.demo.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

class LayersVM() : ScreenModel {
    private val tileStreamProvider =
        makeTileStreamProvider("mont_blanc")
    private val satelliteProvider =
        makeTileStreamProvider("mont_blanc_satellite")
    private val ignV2Provider =
        makeTileStreamProvider("mont_blanc_ignv2")

    private var satelliteId: String? = null
    private var ignV2Id: String? = null

    val state = MapState(4, 4096, 4096).apply {
        shouldLoopScale = true
        enableRotation()
        screenModelScope.launch {
            scrollTo(0.5, 0.5, 1f)
        }

        addLayer(tileStreamProvider)
        satelliteId = addLayer(satelliteProvider)
        ignV2Id = addLayer(ignV2Provider, 0.5f)
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun makeTileStreamProvider(folder: String) =
        TileStreamProvider { row, col, zoomLvl ->
            try {
                Res.readBytes("files/tiles/$folder/$zoomLvl/$row/$col.jpg")
            } catch (e: Exception) {
                null
            }
        }

    fun setSatelliteOpacity(opacity: Float) {
        satelliteId?.also { id ->
            state.setLayerOpacity(id, opacity)
        }

    }

    fun setIgnV2Opacity(opacity: Float) {
        ignV2Id?.also { id ->
            state.setLayerOpacity(id, opacity)
        }
    }
}