package dev.kreienbuehl.mapcompose.utils

import java.util.UUID

actual fun generateId(): String = UUID.randomUUID().toString()