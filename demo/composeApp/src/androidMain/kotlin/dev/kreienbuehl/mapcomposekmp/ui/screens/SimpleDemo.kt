package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.SimpleDemoVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MapDemoSimple(
    modifier: Modifier = Modifier, viewModel: SimpleDemoVM = viewModel()
) {
    MapUI(modifier, state = viewModel.state)
}
