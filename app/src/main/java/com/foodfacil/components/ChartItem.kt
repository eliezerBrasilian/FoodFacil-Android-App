package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.R
import com.foodfacil.dataClass.Salgado
import com.foodfacil.ui.theme.MainYellow
import com.simpletext.SimpleText

@Composable
 fun ChartItem(salgado: Salgado, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundedImage(image = R.drawable.combo_p_transparent)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = salgado.title,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp
            )
            SimpleText(
                title = salgado.description,
                color = Color.DarkGray,
                fontWeight = "300",
                fontSize = 18,
                maxLength = 11
            )
            Text(
                text = "R$ " + salgado.priceInOffer.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
        }
        Right(salgado)
    }
}

@Composable
fun Right(salgado: Salgado) {
    Contador(iconColor = MainYellow, backgroundColor = Color(0xffF6F6F6), iconSize = 20, value = salgado.amount)
}
