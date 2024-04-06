package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText

@Composable
fun MonteSeuPedido(md: Modifier) {
    Box(
        md
            .background(Color(0xffF1F1F1))
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SimpleText("Monte seu pedido:", fontWeight = "bold", fontSize = 15)
            SimpleText(
                "Escolha de 1 até 5 opções",
                fontSize = 15,
                color = Color((0xff555353))
            )
        }
    }
}