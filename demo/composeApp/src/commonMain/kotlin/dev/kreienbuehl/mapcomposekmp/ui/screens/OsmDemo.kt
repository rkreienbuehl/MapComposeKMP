package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcomposekmp.viewmodels.OsmVM

object OsmDemo : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { OsmVM() }

        MapUI(
            Modifier,
            state = screenModel.state
        )
    }
}