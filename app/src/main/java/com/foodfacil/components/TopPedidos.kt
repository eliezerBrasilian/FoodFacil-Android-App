package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodfacil.ui.theme.MainGray
import com.foodfacil.ui.theme.MainRed
import com.simpletext.SimpleText

@Composable
fun TopPedidos(md: Modifier) {
    Box(
        modifier = md
            .fillMaxWidth()
            .height(90.dp)
            .background(MainGray),
        contentAlignment = Alignment.Center
    ) {
        SimpleText("Meus Pedidos", color = MainRed, fontSize = 17)
    }
}