package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.MarkersLazyLoadingVM
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.kreienbuehl.mapcompose.ui.MapUI

@Composable
fun MarkersLazyLoadingDemo(modifier: Modifier = Modifier, viewModel: MarkersLazyLoadingVM = viewModel()) {
    MapUI(modifier, state = viewModel.state)
}