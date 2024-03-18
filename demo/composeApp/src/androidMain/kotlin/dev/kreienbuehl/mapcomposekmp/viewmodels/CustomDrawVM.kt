package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addMarker
import dev.kreienbuehl.mapcompose.api.enableMarkerDrag
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcompose.api.moveMarker
import dev.kreienbuehl.mapcompose.api.scale
import dev.kreienbuehl.mapcompose.api.scrollTo
import dev.kreienbuehl.mapcompose.api.setStateChangeListener
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcomposekmp.ui.screens.ScaleIndicatorController
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcompose.ui.state.MapState

/**
 * In this example, we're adding two markers with custom drag interceptors which update [p1x], [p1y],
 * [p2x], and [p2y] states. In turn, when those state change, the line joining the two markers updates.
 * The line is added as a custom view inside [MapUI] composable.
 */
class CustomDrawVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    var p1x by mutableStateOf(0.6)
    var p1y by mutableStateOf(0.6)
    var p2x by mutableStateOf(0.4)
    var p2y by mutableStateOf(0.4)

    val state = MapState(4, 4096, 4096).apply {
        addLayer(tileStreamProvider)
        shouldLoopScale = true
        enableRotation()
        viewModelScope.launch {
            scrollTo(0.5, 0.5, 1.1f)
        }
    }

    val scaleIndicatorController = ScaleIndicatorController(450, state.scale)

    init {
        state.addMarker("m1", p1x, p1y, Offset(-0.5f, -0.5f)) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xAAF44336))
            )
        }
        state.addMarker("m2", p2x, p2y, Offset(-0.5f, -0.5f)) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xAAF44336))
            )
        }
        state.enableMarkerDrag("m1") { id, x, y, dx, dy, _, _ ->
            p1x = x + dx
            p1y = y + dy
            state.moveMarker(id, p1x, p1y)
        }
        state.enableMarkerDrag("m2") { id, x, y, dx, dy, _, _ ->
            p2x = x + dx
            p2y = y + dy
            state.moveMarker(id, p2x, p2y)
        }
        state.setStateChangeListener {
            scaleIndicatorController.onScaleChanged(scale)
        }
    }
}