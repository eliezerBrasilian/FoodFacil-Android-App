package com.foodfacil.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text

@Composable
fun DataDeCriacaoText(createdAt: Long) {
    Text(
        text =  createdAt.toString(),
        style = MaterialTheme.typography.h4,
        color = Color(0xff545454),
        fontSize = 13.sp
    )
}