package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kreienbuehl.mapcomposekmp.viewmodels.CenteringOnMarkerVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CenteringOnMarkerDemo(
    modifier: Modifier = Modifier,
    viewModel: CenteringOnMarkerVM = viewModel(),
    onCenter: () -> Unit = viewModel::onCenter
) {
    Column(modifier.fillMaxSize()) {
        MapUI(
            modifier.weight(2f),
            state = viewModel.state
        )
        Button(onClick = {
            onCenter()
        }, Modifier.padding(8.dp)) {
            Text(text = "Center on marker")
        }
    }
}