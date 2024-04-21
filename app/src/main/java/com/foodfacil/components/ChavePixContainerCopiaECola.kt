package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

@Composable
fun ChavePixContainerCopiaECola(codigoPix: String, onClick: () -> Unit) {
    val md = Modifier

    Box(
        modifier = md
            .border(1.dp, Color(0xffFF0303), RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = md.fillMaxWidth()
        ) {
            Text(
                text = codigoPix.take(40 - 3) + "...",
                fontWeight = FontWeight.Normal,
                color = Color.Black, fontSize = 16.sp,
            )
            Image(painter = painterResource(id = R.drawable.copia_cola_amarelo),
                contentDescription = null,
                md
                    .size(27.dp)
                    .clickable { onClick() })
        }
    }

}