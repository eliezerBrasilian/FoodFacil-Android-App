package com.foodfacil.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.ui.theme.MainRed

@Composable
fun MetodoDePagamentoColumn(
    md: Modifier = Modifier,
    pixEstaSelecionado: Boolean,
    onClick: () -> Unit = {}
) {
    Column(modifier = md.fillMaxWidth()){
        Spacer(md.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = md.fillMaxWidth()){
            Text(
                text = "Método de Pagamento", color = Color.Black,
                fontSize = 17.sp, fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Trocar", color = MainRed,
                fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = md.clickable { onClick() }
            )
        }

        Spacer(md.height(10.dp))

        if(pixEstaSelecionado){
            PixContainer()
        }

        else{
            PagarNoDinheiroContainer(borderColor = Color.LightGray)
        }
        Spacer(md.height(15.dp))
    }
}