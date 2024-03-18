package dev.kreienbuehl.mapcomposekmp.ui.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.kreienbuehl.mapcomposekmp.R

@Composable
fun Marker() = Icon(
    painter = painterResource(id = R.drawable.map_marker),
    contentDescription = null,
    modifier = Modifier.size(50.dp),
    tint = Color(0xCC2196F3)
)