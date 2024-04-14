package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.foodfacil.ui.theme.MainRed

@Composable
fun Rectangle(imagem: String?) {
    val md = Modifier
    Box(
        modifier = md
            .padding(15.dp)
            .width(90.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = MainRed, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(model  = imagem, contentDescription = null, md.size(50.dp))
    }
}