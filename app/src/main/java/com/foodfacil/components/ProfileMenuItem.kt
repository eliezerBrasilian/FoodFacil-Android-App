package com.foodfacil.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileMenuItem(md: Modifier, title: String,
                    icon:ImageVector, iconImage:Int, onClick:()->Unit){
    Box(
        md
            .height(80.dp)
            .fillMaxWidth()
            .padding(start = 20.dp)
            .clickable {onClick() }, contentAlignment = Alignment.CenterStart) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocalImage(imageResource = iconImage, size = 16.dp)
           // Icon(imageVector = icon, contentDescription = null, md.size(23.dp))
            Text(
               title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}