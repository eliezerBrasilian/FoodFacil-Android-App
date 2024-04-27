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
import com.foodfacil.dataclass.Salgado
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.utils.toBrazilianCurrency

@Composable
 fun ChartItem(
    salgado: Salgado, modifier: Modifier = Modifier,
    increment: (salgado: Salgado) -> Unit = { s -> },
    decrement: (salgado: Salgado) -> Unit = { s -> }
) {
     val preco = if(salgado.emOferta)salgado.precoEmOferta else salgado.preco

    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundedImage(imageUrl = salgado.imagem, size = 79.dp)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = salgado.nome,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
            Text(
                text = salgado.descricao.take(25 - 3) + "...",
                color = Color(0xff555353),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Text(
                text = toBrazilianCurrency(preco * salgado.amount),
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        Right(MainYellow, MainYellow, salgado, increment, decrement)
    }
}

@Composable
fun Right(
    iconAddColor:Color,
    iconSubtractColor:Color,
    salgado: Salgado,
    increment: (salgado: Salgado) -> Unit = { s -> },
    decrement: (salgado: Salgado) -> Unit = { s -> }) {
    Contador(
        iconSize = 15.dp,
        iconAddColor = iconAddColor,
        iconSubtractColor = iconSubtractColor,
        containerHeight = 30.dp,
        containerWidth = 60.dp,
        value = salgado.amount,
        backgroundColor = Color(0xffF6F6F6),
        increment = increment,
        decrement = decrement,
        salgadoId = salgado.id,
        salgado = salgado,
    )
}
