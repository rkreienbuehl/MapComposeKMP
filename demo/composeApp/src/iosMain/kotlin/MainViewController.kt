import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.ComposeUIViewController
import dev.kreienbuehl.mapcompose.ui.MapUI
import dev.kreienbuehl.mapcompose.utils.density
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun iOSApp() {
    density = LocalDensity.current
    println(density)
    var viewModel = remember {OsmVM()}

    MaterialTheme {
        MapUI(state = viewModel.state)
    }
}


fun MainViewController() = ComposeUIViewController { iOSApp() }