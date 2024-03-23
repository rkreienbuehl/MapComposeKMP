package dev.kreienbuehl.mapcomposekmp.viewmodels

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import dev.kreienbuehl.mapcompose.api.*
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import mapcomposekmp.demo.composeapp.generated.resources.Res
import mapcomposekmp.demo.composeapp.generated.resources.map_marker
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class AddingMarkerVM() : ScreenModel {
    private val tileStreamProvider = makeTileStreamProvider()

    private var markerCount = 0

    val state = MapState(4, 4096, 4096) {
        scale(0f) // zoom-out to minimum scale
    }.apply {
        addLayer(tileStreamProvider)
        onMarkerMove { id, x, y, _, _ ->
            println("move $id $x $y")
        }
        onMarkerClick { id, x, y ->
            println("marker click $id $x $y")
        }
        onMarkerLongPress { id, x, y ->
            println("on marker long press $id $x $y")
        }
        onTap { x, y ->
            println("on tap $x $y")
        }
        onLongPress { x, y ->
            println("on long press $x $y")
        }
        enableRotation()
        setScrollOffsetRatio(0.5f, 0.5f)
    }


    @OptIn(ExperimentalResourceApi::class)
    fun addMarker() {
        state.addMarker("marker$markerCount", 0.5, 0.5) {
            Icon(
                painter = painterResource(Res.drawable.map_marker),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xCC2196F3)
            )
        }
        state.enableMarkerDrag("marker$markerCount")
        markerCount++
    }
}