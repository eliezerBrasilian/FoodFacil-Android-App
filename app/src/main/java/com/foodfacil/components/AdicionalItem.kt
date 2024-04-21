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
import com.foodfacil.dataclass.Adicional
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.utils.toBrazilianCurrency

@Composable
fun AdicionalItem(
    adicional: Adicional, modifier: Modifier = Modifier,
    increment: (adicional: Adicional) -> Unit = { a -> },
    decrement: (adicional: Adicional) -> Unit = { a -> }
) {

    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundedImage(imageUrl = adicional.imagem, size = 79.dp)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = adicional.titulo,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
            Text(
                text = adicional.descricao.take(25 - 3) + "...",
                color = Color(0xff555353),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Text(
                text = toBrazilianCurrency(adicional.preco * adicional.amount),
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        AdicionalItemRight(MainYellow, MainYellow, adicional, increment, decrement)
    }
}

@Composable
fun AdicionalItemRight(
    iconAddColor: Color,
    iconSubtractColor: Color,
    adicional: Adicional,
    increment: (adicional: Adicional) -> Unit = { a -> },
    decrement: (adicional: Adicional) -> Unit = { a -> }) {
    ContadorAdicionalItem(
        iconAddColor = iconAddColor,
        iconSubtractColor = iconSubtractColor,
        backgroundColor = Color(0xffF6F6F6),
        iconSize = 15.dp,
        containerHeight = 30.dp,
        containerWidth = 60.dp,
        value = adicional.amount,
        adicional = adicional,
        increment = increment,
        decrement = decrement)
}