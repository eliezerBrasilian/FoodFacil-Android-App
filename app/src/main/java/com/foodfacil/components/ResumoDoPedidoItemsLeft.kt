package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumoDoPedidoItemsLeft() {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = "Subtotal",
            fontSize = 14.sp, fontWeight = FontWeight.Light,
            color = Color.Black
        )
        Text(
            text = "Taxa de entrega",
            fontSize = 14.sp, fontWeight = FontWeight.Light,
            color = Color.Black
        )
        Text(
            text = "Descontos",
            fontSize = 14.sp, fontWeight = FontWeight.Light,
            color = Color.Black
        )
        Text(
            text = "Total",
            fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}