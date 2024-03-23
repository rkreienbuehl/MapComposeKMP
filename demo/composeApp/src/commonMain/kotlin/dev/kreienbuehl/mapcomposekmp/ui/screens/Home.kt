package dev.kreienbuehl.mapcomposekmp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.kreienbuehl.mapcomposekmp.ui.MainDestinations
import mapcomposekmp.demo.composeapp.generated.resources.Res
import mapcomposekmp.demo.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

object HomeScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val demoListState = rememberLazyListState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    backgroundColor = MaterialTheme.colors.primarySurface,
                )
            }
        ) { padding ->
            LazyColumn(
                Modifier.padding(padding),
                state = demoListState
            ) {
                MainDestinations.values().map { dest ->
                    item {
                        Text(
                            text = dest.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navigator.push(dest.screen) }
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Home(demoListState: LazyListState, onDemoSelected: (dest: MainDestinations) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primarySurface,
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            state = demoListState
        ) {
            MainDestinations.values().map { dest ->
                item {
                    Text(
                        text = dest.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDemoSelected.invoke(dest) }
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Divider(thickness = 1.dp)
                }
            }
        }
    }
}