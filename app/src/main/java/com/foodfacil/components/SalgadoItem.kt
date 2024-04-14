package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodfacil.dataClass.SalgadoResponseDto
import com.foodfacil.graphs.DetailsScreen
import com.simpletext.SimpleText

@Composable
fun SalgadoItem(md: Modifier, salgado: SalgadoResponseDto,
                navController: NavHostController,
                leftWidth:Dp = 180.dp,
                imageWidth:Dp = 170.dp
                ){
   Box(modifier =
   md.background(Color(0xffF1F1F1),
       shape = RoundedCornerShape(12.dp)).clickable{
       navController.navigate(DetailsScreen.Information.route + "/${salgado.id}")
   }){
       Row(horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.fillMaxWidth().padding(15.dp)) {
           Column(verticalArrangement = Arrangement.spacedBy(15.dp),
               modifier = Modifier.width(leftWidth)) {
               Title(salgado.name)
               Description(text = salgado.description)
               Price(price = salgado.priceInOffer)
           }

           AsyncImage(model = salgado.image,
                   contentDescription = null,
                   contentScale = ContentScale.Fit,
                   modifier = Modifier.width(imageWidth))


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