package dev.kreienbuehl.mapcomposekmp.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addMarker
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcomposekmp.ui.widgets.Marker
import dev.kreienbuehl.mapcompose.ui.state.MapState

class VisibleAreaPaddingVM() : ScreenModel {
    private val tileStreamProvider = makeTileStreamProvider()

    val state = MapState(4, 4096, 4096) {
        scale(1.2f)
    }.apply {
        enableRotation()
        addLayer(tileStreamProvider)
        addMarker("m0", 0.5, 0.5) { Marker() }
    }
}
