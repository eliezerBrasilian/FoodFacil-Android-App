package com.foodfacil.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomIcon(icon:ImageVector = Icons.Default.Check,modifier: Modifier = Modifier, ){
    Icon(imageVector = icon, contentDescription = null, modifier = modifier)
}