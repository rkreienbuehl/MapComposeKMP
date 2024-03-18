package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kreienbuehl.mapcomposekmp.viewmodels.CalloutVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CalloutDemo(
    modifier: Modifier = Modifier,
    viewModel: CalloutVM = viewModel()
) {
    Column(modifier.fillMaxSize()) {
        MapUI(
            modifier.weight(2f),
            state = viewModel.state
        )
    }
}
