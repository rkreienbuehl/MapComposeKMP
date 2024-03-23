package dev.kreienbuehl.mapcomposekmp.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import dev.kreienbuehl.mapcompose.api.addLayer
import dev.kreienbuehl.mapcompose.api.scale
import dev.kreienbuehl.mapcompose.api.shouldLoopScale
import dev.kreienbuehl.mapcompose.core.TileStreamProvider
import dev.kreienbuehl.mapcompose.ui.state.MapState
import dev.kreienbuehl.mapcomposekmp.utils.getKtorClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes

/**
 * Shows how MapCompose behaves with remote HTTP tiles.
 */
class HttpTilesVM() : ScreenModel {
    private val httpClient = getKtorClient()
    private val tileStreamProvider = makeTileStreamProvider(httpClient)

    val state = MapState(
        levelCount = 4,
        fullWidth = 4096,
        fullHeight = 4096,
        workerCount = 16  // Notice how we increase the worker count when performing HTTP requests
    ).apply {
        addLayer(tileStreamProvider)
        scale = 0f
        shouldLoopScale = true
    }
}

/**
 * A [TileStreamProvider] which performs HTTP requests.
 */
private fun makeTileStreamProvider(httpClient: HttpClient) =
    TileStreamProvider { row, col, zoomLvl ->
        try {
            val response: HttpResponse = httpClient.get("https://a.basemaps.cartocdn.com/rastertiles/voyager/$zoomLvl/$col/$row@2x.png")
            response.readBytes()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }