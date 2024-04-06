package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Circle(modifier: Modifier = Modifier, color: Color = Color.Red, size: Dp = 200.dp, onClick:()->Unit = {}, child: @Composable ()-> Unit, ){
    Box(
        modifier = modifier.background(color, CircleShape).size(size).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        child()
    }
}