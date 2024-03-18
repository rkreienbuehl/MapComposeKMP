package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kreienbuehl.mapcomposekmp.viewmodels.OsmVM

@Composable
fun OsmDemo(
    modifier: Modifier = Modifier, viewModel: OsmVM = viewModel()
) {
    MapUI(
        modifier,
        state = viewModel.state
    )
}