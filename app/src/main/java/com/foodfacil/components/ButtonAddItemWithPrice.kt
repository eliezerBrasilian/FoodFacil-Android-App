package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.utils.toBrazilianCurrency
import com.simpletext.SimpleText

@Composable
fun ButtonAddItemWithPrice(total: Float, onClick:()->Unit = {}) {
    val md = Modifier
    Box(
        md
            .fillMaxWidth()
            .padding(horizontal = 20.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(MainRed),
            contentPadding = PaddingValues(15.dp),
            shape = MaterialTheme.shapes.large,
            modifier = md.width(250.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically, modifier = md.fillMaxWidth()
            ) {
                SimpleText("Adicionar", color = Color.White, fontSize = 17)
                SimpleText(toBrazilianCurrency(total), color = Color.White, fontWeight = "400", fontSize = 16)
            }
        }
    }
}