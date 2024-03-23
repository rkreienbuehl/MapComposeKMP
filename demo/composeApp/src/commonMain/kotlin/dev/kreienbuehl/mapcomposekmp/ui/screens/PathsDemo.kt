package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.kreienbuehl.mapcomposekmp.viewmodels.PathsVM
import dev.kreienbuehl.mapcompose.ui.MapUI

object PathsDemo : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { PathsVM() }

        MapUI(
            Modifier,
            state = screenModel.state
        )
    }
}