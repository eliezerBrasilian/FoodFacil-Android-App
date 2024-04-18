package com.foodfacil.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.foodfacil.ui.theme.MainRed

@Composable
fun AddressRow(complemento: String, numero: String, rua: String, bairro: String) {
    val md = Modifier
    Box(
        modifier = md
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = MainRed, RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = md.fillMaxWidth()
        ) {
            Left(rua = rua, numero = numero, bairro = bairro, complemento = complemento)
            Image(
                painter = painterResource(id = R.drawable.custom_yellow_check),
                contentDescription = null,
                md.size(17.dp)
            )
        }

    }
}

@Composable
private fun Left(rua:String, numero:String, bairro:String, complemento:String){
    val md = Modifier
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Circle(size = 30.dp, color = Color(0xffF1F1F1)) {
            Image(
                painter = painterResource(id = R.drawable.localizacao_yellow),
                contentDescription = null,
                md.size(20.dp)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(if(rua.contains("rua") || rua.contains("Rua"))" $rua, $numero" else "Rua $rua, $numero",
                fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(bairro, fontWeight = FontWeight.Normal, fontSize = 15.sp)
            if(complemento.isNotEmpty())
            Text(complemento, fontWeight = FontWeight.Normal, fontSize = 15.sp)
        }

    }
}