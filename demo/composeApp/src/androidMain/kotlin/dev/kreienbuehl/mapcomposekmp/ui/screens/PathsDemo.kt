package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.PathsVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PathsDemo(
    modifier: Modifier = Modifier, viewModel: PathsVM = viewModel()
) {
    MapUI(
        modifier,
        state = viewModel.state
    )
}