package com.foodfacil.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.ui.theme.MainRed

@Composable
fun CopiarCodigoPixButton(onClick: () -> Unit = {}) {
    val md = Modifier

    Button(
        onClick = onClick,
        modifier = md.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MainRed),
        contentPadding = PaddingValues(vertical = 15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = "Copiar código pix",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}