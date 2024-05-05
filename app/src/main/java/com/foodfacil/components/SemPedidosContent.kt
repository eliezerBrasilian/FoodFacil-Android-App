package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.foodfacil.R
import com.foodfacil.ui.theme.MainRed

@Composable
fun SemPedidosContent(md: Modifier, verCardapioClick: () -> Unit) {
    Box(
        modifier = md
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sem_pedidos_image),
                contentDescription = null
            )
            Text(
                text = "Você ainda não realizou nenhum pedido!",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                text = "Bora Pedir? Acesse o cardápio para ver as opções disponíveis",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = md.height(20.dp))
            Button(
                onClick = { verCardapioClick() },
                modifier = md.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    MainRed
                )
            ) {
                Text(
                    text = "Ver cardápio",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}