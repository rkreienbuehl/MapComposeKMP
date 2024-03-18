import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcompose.utils.density
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun desktopApp() {
    density = LocalDensity.current
    println(density)
    var viewModel = remember {OsmVM()}

    MaterialTheme {
        MapUI(state = viewModel.state)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "MapComposeKMP") {
        desktopApp()
    }
}