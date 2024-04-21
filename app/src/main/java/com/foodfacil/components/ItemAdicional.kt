package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.foodfacil.dataclass.Adicional
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.utils.toBrazilianCurrency

@Composable
fun ItemAdicional(
    adicional: Adicional,
    addAdicionalNoCarrinho: (item: Adicional) -> Unit,
) {
    val md = Modifier

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Box(
            modifier = md
                .background(Color(0xffF6F6F6), RoundedCornerShape(12.dp))
                .padding(15.dp)
                .width(130.dp)
                .height(96.dp)
        ) {
            Box(
                modifier = md
                    .fillMaxWidth()
                    .padding(end = 40.dp), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = adicional.imagem,
                    contentDescription = null,
                    modifier = md
                        .width(70.dp)
                        .height(120.dp)
                )
            }

            Box(modifier = md.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                Circle(color = MainYellow, size = 45.dp, onClick = {
                    addAdicionalNoCarrinho(adicional)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = null,
                        tint = MainRed
                    )
                }
            }
        }

        Column {
            Text(
                text = toBrazilianCurrency(adicional.preco),
                color = Color.Black,
                fontSize = 19.sp,
                maxLines = 1
            )
            Spacer(modifier = md.height(5.dp))
            Text(
                text = adicional.titulo,
                color = Color.DarkGray,
                fontSize = 17.sp,
                maxLines = 1
            )
            Box(modifier = md.fillMaxWidth()) {
                Text(
                    text = adicional.descricao,
                    color = Color.DarkGray,
                    fontSize = 17.sp,
                    maxLines = 1
                )
            }
        }
    }
}