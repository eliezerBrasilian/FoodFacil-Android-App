package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.foodfacil.ui.theme.MainRed

@Composable
 fun SalgadoItemDentroDePedido(
    salgado: SalgadoResumidoResponseDto,
    md: Modifier
) {
    Column(verticalArrangement = Arrangement.Center, modifier = md.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = md.fillMaxWidth()
            ) {
            AsyncImage(
                model = salgado.imagem,
                contentDescription = null,
                modifier = md.size(90.dp)
            )
            Column {
                Text(
                    text = salgado.nome,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.Black,
                    fontSize = 17.sp
                )
                Text(
                    text = "descrição do salgado aqui...",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    fontSize = 17.sp
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = MainRed,
                modifier = md.size(30.dp)
            )
        }
    }
}