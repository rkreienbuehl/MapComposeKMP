package dev.kreienbuehl.mapcomposekmp.providers

import dev.kreienbuehl.mapcompose.core.TileStreamProvider
import mapcomposekmp.demo.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
fun makeTileStreamProvider() =
    TileStreamProvider { row, col, zoomLvl ->
        try {
            Res.readBytes("files/tiles/mont_blanc/$zoomLvl/$row/$col.jpg")
        } catch (e: Exception) {
            null
        }
    }
