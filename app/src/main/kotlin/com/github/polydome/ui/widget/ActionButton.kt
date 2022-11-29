package com.github.polydome.ui.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(modifier: Modifier = Modifier, onClick: () -> Unit, icon: ImageVector) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(32.dp)
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
}