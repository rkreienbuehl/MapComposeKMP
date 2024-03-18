package dev.kreienbuehl.mapcompose.ui.state

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun getProcessorCount(): Int {
    return Platform.getAvailableProcessors()
}