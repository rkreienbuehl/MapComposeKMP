package dev.kreienbuehl.mapcomposekmp.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.rotateTo
import dev.kreienbuehl.mapcompose.api.rotation
import dev.kreienbuehl.mapcompose.api.scale
import dev.kreienbuehl.mapcompose.api.scroll
import dev.kreienbuehl.mapcompose.api.setScrollOffsetRatio
import dev.kreienbuehl.mapcompose.api.setStateChangeListener
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState

class RotationVM() : ScreenModel {
    private val tileStreamProvider = makeTileStreamProvider()

    val state = MapState(4, 4096, 4096).apply {
        addLayer(tileStreamProvider)
        enableRotation()
        setScrollOffsetRatio(0.3f, 0.3f)
        scale = 0f

        /* Not useful here, just showing how this API works */
        setStateChangeListener {
            println("scale: $scale, scroll: $scroll, rotation: $rotation")
        }
    }

    fun onRotate() {
        screenModelScope.launch {
            state.rotateTo(state.rotation + 90f)
        }
    }
}