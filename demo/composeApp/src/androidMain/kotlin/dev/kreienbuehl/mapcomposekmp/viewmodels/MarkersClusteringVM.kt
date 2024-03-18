package dev.kreienbuehl.mapcomposekmp.viewmodels

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import dev.kreienbuehl.mapcompose.api.ExperimentalClusteringApi
import dev.kreienbuehl.mapcompose.api.addClusterer
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.addMarker
import dev.kreienbuehl.mapcomposekmp.providers.makeTileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import dev.kreienbuehl.mapcompose.ui.state.markers.model.RenderingStrategy
import mapcomposekmp.demo.composeapp.generated.resources.Res
import mapcomposekmp.demo.composeapp.generated.resources.map_marker
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


/**
 * Shows how to define and use a marker clusterer.
 */
@OptIn(ExperimentalClusteringApi::class, ExperimentalResourceApi::class)
class MarkersClusteringVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    val state = MapState(4, 4096, 4096) {
        scale(0.2f)
        maxScale(8f)
        scroll(0.5, 0.5)
    }.apply {
        addLayer(tileStreamProvider)
    }

    init {
        /* Add a marker clusterer to manage markers. In this example, we use "default" for the id */
        state.addClusterer("default") { ids ->
            { Cluster(size = ids.size) }
        }

        /* Add some markers to the map, using the same clusterer id we just defined (if a marker
         * is added without any clusterer, it won't be managed by any clusterer)*/
        listOf(
            0.5 to 0.5,
            0.51 to 0.5,
            0.5 to 0.54,
            0.51 to 0.54,
            0.6 to 0.52,
            0.48 to 0.35,
            0.48 to 0.355,
            0.485 to 0.35,
            0.52 to 0.35,
            0.515 to 0.36,
            0.515 to 0.355,
        ).forEachIndexed { i, pair ->
            state.addMarker(
                id = "marker-$i",
                x = pair.first,
                y = pair.second,
                renderingStrategy = RenderingStrategy.Clustering("default"),
            ) {
                Marker()
            }
        }

        /* We can still add regular markers */
        state.addMarker(
            "marker-regular", 0.52, 0.36,
            clickable = false
        ) {
            Icon(
                painter = painterResource(Res.drawable.map_marker),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xEEF44336)
            )
        }
    }

    @Composable
    private fun Marker() {
        Icon(
            painter = painterResource(Res.drawable.map_marker),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = Color(0xEE2196F3)
        )
    }

    @Composable
    private fun Cluster(size: Int) {
        /* Here we can customize the cluster style */
        Box(
            modifier = Modifier
                .background(
                    Color(0x992196F3),
                    shape = CircleShape
                )
                .size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = size.toString(), color = Color.White)
        }
    }
}
