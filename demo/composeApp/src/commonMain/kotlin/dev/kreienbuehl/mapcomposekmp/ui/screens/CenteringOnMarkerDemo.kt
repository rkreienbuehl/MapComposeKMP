package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.kreienbuehl.mapcomposekmp.viewmodels.CenteringOnMarkerVM
import dev.kreienbuehl.mapcompose.ui.MapUI

object CenteringOnMarkerDemo : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { CenteringOnMarkerVM() }
        val onCenter: () -> Unit = screenModel::onCenter

        Column(Modifier.fillMaxSize()) {
            MapUI(
                Modifier.weight(2f),
                state = screenModel.state
            )
            Button(onClick = {
                onCenter()
            }, Modifier.padding(8.dp)) {
                Text(text = "Center on marker")
            }
        }
    }
}