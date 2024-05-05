package com.foodfacil.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnderecoItem(stateValue: String?, titulo: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "$titulo: $stateValue",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}