package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.R

@Composable
fun ChartCupomLeft(cuponsDisponiveisTotal:Int = 2){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cupom),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Column {
            Text("Cupom", fontSize = 17.sp, color = Color.Black, fontWeight = FontWeight.W600)
            Text(
                "$cuponsDisponiveisTotal disponíveis para usar",
                fontSize = 13.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Light
            )
        }
    }
}