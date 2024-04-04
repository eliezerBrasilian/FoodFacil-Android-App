package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foodfacil.dataClass.Salgado
import com.simpletext.SimpleText

@Composable
fun SalgadoItem(md: Modifier, salgado: Salgado){
   Box(modifier = md.background(Color(0xffF1F1F1), shape = RoundedCornerShape(12.dp)).padding(15.dp)){
       Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
           Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
               Title(salgado.title)
               Description(text = salgado.description)
               Price(price = salgado.priceInOffer)
           }
           Image(painter = painterResource(id = salgado.image), contentDescription = null, md.size(100.dp))
       }
   }
}

@Composable
fun Title(title: String) {
    SimpleText(title, fontSize = 18, fontWeight = "bold")
}

@Composable
fun Description(text: String) {
    SimpleText(text, fontSize = 17, fontWeight = "300", color = Color(0xff555353))
}

@Composable
fun Price(price: Float) {
    val formattedValue = "R$ $price"
    SimpleText(formattedValue, fontSize = 17, fontWeight = "bold", color = Color(0xff28D84F))
}