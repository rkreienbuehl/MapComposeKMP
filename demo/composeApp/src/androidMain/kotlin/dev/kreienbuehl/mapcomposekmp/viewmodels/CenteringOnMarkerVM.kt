package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addMarker
import dev.kreienbuehl.mapcompose.api.centerOnMarker
import dev.kreienbuehl.mapcompose.api.enableRotation
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import mapcomposekmp.demo.composeapp.generated.resources.Res
import mapcomposekmp.demo.composeapp.generated.resources.map_marker
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class CenteringOnMarkerVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    @OptIn(ExperimentalResourceApi::class)
    val state = MapState(4, 4096, 4096) {
        rotation(45f)
    }.apply {
        addLayer(tileStreamProvider)
        addMarker("parking", 0.2457938, 0.3746023) {
            Icon(
                painter = painterResource(Res.drawable.map_marker),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xCC2196F3)
            )
        }
        enableRotation()
    }

    fun onCenter() {
        viewModelScope.launch {
            state.centerOnMarker("parking", destScale = 1f, destAngle = 0f)
        }
    }
}