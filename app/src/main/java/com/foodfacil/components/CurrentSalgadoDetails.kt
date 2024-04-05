package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.foodfacil.dataClass.Salgado
import com.foodfacil.ui.theme.GreenDot
import com.simpletext.SimpleText


@Composable
fun CurrentSalgadoDetails(
    md: Modifier.Companion,
    salgadoSelected: MutableState<Salgado?>,
    priceFormated: MutableState<String>
) {
    Column(
        md
            .background(Color.White)
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        SimpleText(
            salgadoSelected.value?.title.toString(),
            fontWeight = "400",
            fontSize = 23
        )
        SimpleText(
            salgadoSelected.value?.description.toString(),
            fontWeight = "300",
            fontSize = 16
        )
        SimpleText(
            priceFormated.value,
            fontWeight = "bold",
            fontSize = 16,
            color = GreenDot
        )

    }
}