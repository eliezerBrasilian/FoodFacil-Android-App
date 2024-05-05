package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodfacil.dataclass.Salgado
import com.foodfacil.navigation.DetailsScreen
import com.foodfacil.utils.toBrazilianCurrency

@Composable
fun SalgadoItem(
    md: Modifier, salgado: Salgado,
    navController: NavHostController,
    leftWidth:Dp = 180.dp,
    imageWidth:Dp = 100.dp
                ){
   Box(
       modifier = md.height(125.dp).background(Color(0xffF1F1F1),
       shape = RoundedCornerShape(12.dp)).clickable{
       navController.navigate(DetailsScreen.Information.route + "/${salgado.id}")
   }){
       Row(horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.fillMaxWidth(1f).padding(15.dp)) {
           Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
               Title(salgado.nome)
               Box(modifier = Modifier.width(250.dp)){
                   Description(text = salgado.descricao)
               }
               Price(price = salgado.precoEmOferta)
           }
        Box(modifier = Modifier.width(100.dp)){
            AsyncImage(model = salgado.imagem,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(imageWidth))
        }
       }
   }
}

@Composable
fun Title(title: String) {
    Text(title, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}

@Composable
fun Description(text: String) {
    Text(text, color = Color(0xff555353), fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 15.sp,
        modifier = Modifier, textAlign = TextAlign.Start)
}

@Composable
fun Price(price: Float) {
    Text(toBrazilianCurrency(price), color = Color(0xff28D84F), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}