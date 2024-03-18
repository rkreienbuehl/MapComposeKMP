package dev.kreienbuehl.mapcompose.ui.state

actual fun getProcessorCount(): Int {
    return Runtime.getRuntime().availableProcessors()
}