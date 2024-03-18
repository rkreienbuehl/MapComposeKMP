package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.MarkersClusteringVM
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kreienbuehl.mapcompose.ui.MapUI

@Composable
fun MarkersClusteringDemo(modifier: Modifier = Modifier, viewModel: MarkersClusteringVM = viewModel()) {
    MapUI(modifier, state = viewModel.state)
}