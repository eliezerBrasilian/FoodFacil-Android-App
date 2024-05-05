package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText

@Composable
 fun ImageButtonsRow(descartar:()->Unit = {}, salvar:()-> Unit = {}){
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = descartar, colors = ButtonDefaults.buttonColors(Color.LightGray)) {
            SimpleText("Descartar", fontSize = 16, color = Color.White)
        }
        Button(onClick = salvar) {
            SimpleText("Salvar", fontSize = 16, color = Color.White)
        }
    }
}