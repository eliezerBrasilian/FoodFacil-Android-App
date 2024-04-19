package com.foodfacil.dataclass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodfacil.components.Line
import com.foodfacil.graphs.DetailsScreen
import com.foodfacil.utils.toBrazilianCurrency

@Composable
fun CardapioItem(
    md: Modifier, salgado: SalgadoDto,
    navController: NavHostController,
){
    Box(
        modifier = md
            .clickable {
                navController.navigate(DetailsScreen.Information.route + "/${salgado.id}")
            }){
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                AsyncImage(model = salgado.imageQuadrada,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(110.dp))

                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Title(salgado.name)
                    Description(text = salgado.description)
                    Price(price = if(salgado.inOffer) salgado.priceInOffer else salgado.price)
                }
            }
            Spacer(modifier = md.height(15.dp))
            Line()
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