package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState

class SimpleDemoVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    val state = MapState(4, 4096, 4096) {
        scale(1.2f)
    }.apply {
        addLayer(tileStreamProvider)
        shouldLoopScale = true
        enableRotation()
    }
}