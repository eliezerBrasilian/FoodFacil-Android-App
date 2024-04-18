package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.utils.toBrazilianCurrency

@Composable
 fun ResumoDoPedidoItemsRight(subtotal:Float = 0f, cupomFreteGratisAplicado:Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = toBrazilianCurrency(subtotal),
            fontSize = 14.sp, fontWeight = FontWeight.Thin,
            color = Color.DarkGray
        )
        Text(
            text = if (cupomFreteGratisAplicado) "Grátis" else toBrazilianCurrency(2.00f),
            fontSize = 14.sp, fontWeight = FontWeight.Thin,
            color = Color.DarkGray
        )
        Text(
            text = if (cupomFreteGratisAplicado) toBrazilianCurrency(subtotal - 2.00f) else toBrazilianCurrency(
                0f
            ),
            fontSize = 14.sp, fontWeight = FontWeight.Thin,
            color = Color.DarkGray
        )
        Text(
            text = toBrazilianCurrency(subtotal + 2.00f),
            fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}