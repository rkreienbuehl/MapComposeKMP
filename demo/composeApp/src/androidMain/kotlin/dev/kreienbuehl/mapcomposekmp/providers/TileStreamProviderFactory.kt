package dev.kreienbuehl.mapcomposekmp.providers

import android.content.Context
import dev.kreienbuehl.mapcompose.core.TileStreamProvider

fun makeTileStreamProvider(appContext: Context) =
    TileStreamProvider { row, col, zoomLvl ->
        try {
            appContext.assets?.open("tiles/mont_blanc/$zoomLvl/$row/$col.jpg")?.readBytes()
        } catch (e: Exception) {
            null
        }
    }
