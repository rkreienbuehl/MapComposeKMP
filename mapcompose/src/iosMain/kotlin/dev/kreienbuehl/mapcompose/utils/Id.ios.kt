package dev.kreienbuehl.mapcompose.utils

import platform.Foundation.NSUUID

actual fun generateId(): String = NSUUID().UUIDString()