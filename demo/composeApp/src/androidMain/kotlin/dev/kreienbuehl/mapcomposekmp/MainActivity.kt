package dev.kreienbuehl.mapcomposekmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import dev.kreienbuehl.mapcomposekmp.ui.screens.HomeScreen
import dev.kreienbuehl.mapcomposekmp.ui.theme.MapComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapComposeTheme {
                Navigator(HomeScreen)
            }
        }
    }
}