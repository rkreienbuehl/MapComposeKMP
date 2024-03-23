package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.kreienbuehl.mapcomposekmp.viewmodels.MarkersClusteringVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcomposekmp.viewmodels.LayersVM

object MarkersClusteringDemo : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MarkersClusteringVM() }

        MapUI(Modifier, state = screenModel.state)
    }
}