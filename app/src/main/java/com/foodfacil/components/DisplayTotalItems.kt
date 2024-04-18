package com.foodfacil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.simpletext.SimpleText

@Composable
fun DisplayTotalItems(first: String, second: String, third: String) {
    Column {
        SimpleText(first, fontSize = 18)
        SimpleText(second, fontSize = 18)
        SimpleText(third, fontWeight = "bold", fontSize = 21)
    }
}