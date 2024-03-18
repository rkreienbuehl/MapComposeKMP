package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import dev.kreienbuehl.mapcompose.api.ExperimentalClusteringApi
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addLazyLoader
import dev.kreienbuehl.mapcompose.api.addMarker
import dev.kreienbuehl.mapcompose.api.onMarkerClick
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcompose.ui.layout.Forced
import dev.kreienbuehl.mapcompose.ui.state.MapState
import dev.kreienbuehl.mapcompose.ui.state.markers.model.RenderingStrategy
import mapcomposekmp.demo.composeapp.generated.resources.Res
import mapcomposekmp.demo.composeapp.generated.resources.map_marker
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

/**
 * Shows how to define and use a marker lazy-loader.
 */
@OptIn(ExperimentalClusteringApi::class, ExperimentalResourceApi::class)
class MarkersLazyLoadingVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider =
        dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider(application.applicationContext)

    val state = MapState(4, 4096, 4096) {
        minimumScaleMode(Forced(1f))
        scale(1f)
        maxScale(4f)
        scroll(0.5, 0.5)
    }.apply {
        addLayer(tileStreamProvider)
        shouldLoopScale = true
    }

    init {
        /* Add a marker lazy loader. In this example, we use "default" for the id */
        state.addLazyLoader("default")

        repeat(200) { i ->
            val x = Random.nextDouble()
            val y = Random.nextDouble()

            /* Notice how we set the rendering strategy to lazy loading with the same id */
            state.addMarker(
                "marker-$i", x, y,
                renderingStrategy = RenderingStrategy.LazyLoading(lazyLoaderId = "default")
            ) {
                Icon(
                    painter = painterResource(Res.drawable.map_marker),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = Color(0xEE2196F3)
                )
            }
        }

        /* We can still add regular markers */
        state.addMarker(
            "marker-regular", 0.5, 0.5,
        ) {
            Icon(
                painter = painterResource(Res.drawable.map_marker),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xEEF44336)
            )
        }

        state.onMarkerClick { id, x, y ->
            println("marker click $id $x $y")
        }
    }
}