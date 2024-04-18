package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumoDoPedidoItems(subtotal: Float) {
    val md = Modifier
    Column(modifier = md.fillMaxWidth()) {
        Spacer(md.height(20.dp))
        Line()
        Spacer(md.height(20.dp))
        Text(text ="Resumo do pedido",
            fontSize = 16.sp, fontWeight = FontWeight.Bold,
            color  = Color.DarkGray )
        Spacer(md.height(10.dp))
    }
    Row(modifier = md.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        ResumoDoPedidoItemsLeft()
        ResumoDoPedidoItemsRight(subtotal = subtotal)
    }
    Spacer(md.height(15.dp))
    Line()
    Spacer(md.height(20.dp))
}