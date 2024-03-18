package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.kreienbuehl.mapcomposekmp.viewmodels.AddingMarkerVM
import dev.kreienbuehl.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddingMarkerDemo(
    modifier: Modifier = Modifier,
    viewModel: AddingMarkerVM = viewModel(),
) {
    Column(modifier.fillMaxSize()) {
        MapUI(
            modifier.weight(2f),
            state = viewModel.state
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                viewModel.addMarker()
            }, Modifier.padding(8.dp)) {
                Text(text = "Add marker")
            }

            Text("Drag markers with finger")
        }
    }
}