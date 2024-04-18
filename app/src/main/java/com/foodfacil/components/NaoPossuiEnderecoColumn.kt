package com.foodfacil.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.ui.theme.MainRed

@Composable
fun NaoPossuiEnderecoColumn(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Você ainda não possui um endereço de entrega realize seu cadastro!",
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }) {
                Circle(
                    modifier =
                    Modifier.border(width = 1.dp, color = MainRed, shape = CircleShape),
                    size = 25.dp, color = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = null,
                        tint = MainRed, modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Adicionar Endereço de Entrega", color = MainRed,
                    fontSize = 16.sp, modifier = Modifier.clickable{onClick()}
                )
            }
            Line()
        }
    }
}