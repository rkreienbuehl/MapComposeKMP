package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.HttpTilesVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HttpTilesDemo(
    modifier: Modifier = Modifier, viewModel: HttpTilesVM = viewModel()
) {
    MapUI(
        modifier,
        state = viewModel.state
    )
}