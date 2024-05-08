package com.foodfacil.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.foodfacil.ui.theme.MainYellow

@Composable
fun CustomButton(
    @SuppressLint("ModifierParameter") buttonModifier: Modifier = Modifier.fillMaxWidth(),
    textModifier: Modifier = Modifier,
    text:String,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = { onClick?.let { onClick() } },
        colors = ButtonDefaults.buttonColors(MainYellow),
        modifier = buttonModifier
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            color = Color.White,
            modifier = textModifier
        )
    }
}