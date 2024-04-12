package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foodfacil.R
import com.simpletext.SimpleText

@Composable
fun PixContainer() {
    val md = Modifier
    Box(
        modifier = md
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color.Black, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.simbolopix),
                contentDescription = null,
                md.size(40.dp)
            )
            Column {
                SimpleText("PIX", fontWeight = "bold", fontSize = 19)
                SimpleText(
                    "Aprovação imediata",
                    fontWeight = "300",
                    fontSize = 16,
                    color = Color.DarkGray
                )
            }
            Image(
                painter = painterResource(id = R.drawable.selecionar),
                contentDescription = null,
                md.size(30.dp)
            )
        }

    }
}