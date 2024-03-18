package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.scrollTo
import dev.kreienbuehl.mapcompose.api.setLayerOpacity
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcompose.core.TileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState

class LayersVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider =
        makeTileStreamProvider(application.applicationContext, "mont_blanc")
    private val satelliteProvider =
        makeTileStreamProvider(application.applicationContext, "mont_blanc_satellite")
    private val ignV2Provider =
        makeTileStreamProvider(application.applicationContext, "mont_blanc_ignv2")

    private var satelliteId: String? = null
    private var ignV2Id: String? = null

    val state = MapState(4, 4096, 4096).apply {
        shouldLoopScale = true
        enableRotation()
        viewModelScope.launch {
            scrollTo(0.5, 0.5, 1f)
        }

        addLayer(tileStreamProvider)
        satelliteId = addLayer(satelliteProvider)
        ignV2Id = addLayer(ignV2Provider, 0.5f)
    }

    private fun makeTileStreamProvider(appContext: Context, folder: String) =
        TileStreamProvider { row, col, zoomLvl ->
            try {
                appContext.assets?.open("tiles/$folder/$zoomLvl/$row/$col.jpg")?.readBytes()
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