package com.foodfacil.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
 fun TempoEstimadoDeEntregaRow() {
    Row {
        Text(
            text = "Tempo estimado de entrega: ",
            fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Text(
            text = "15 a 30 minutos",
            fontSize = 16.sp, fontWeight = FontWeight.Light,
            color = Color.Black
        )
    }
}