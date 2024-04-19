package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun ObservacaoTopRow(value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //left
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LocalImage(imageResource = R.drawable.message, size = 17.dp)
            Text(
                text = "Alguma observação?", color = Color(0xff4C4C4C),
                fontWeight = FontWeight.Bold, fontSize = 14.sp
            )
        }
        Text(
            text = "${value.length}/140", color = Color(0xff4C4C4C),
            fontWeight = FontWeight.Bold, fontSize = 14.sp
        )
    }
}