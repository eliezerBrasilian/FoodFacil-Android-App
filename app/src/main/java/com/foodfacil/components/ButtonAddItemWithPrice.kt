package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.utils.toBrazilianCurrency

@Composable
fun ButtonAddItemWithPrice(
    total: Float = 0f,
    onClick: () -> Unit = {},
) {
    val md = Modifier
    Card(elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = md.fillMaxWidth().height(75.dp)) {
       Row(modifier = md.fillMaxWidth().padding(end = 20.dp,top = 10.dp),
           horizontalArrangement = Arrangement.End,
           verticalAlignment = Alignment.CenterVertically) {

           Button(
               onClick = onClick,
               colors = ButtonDefaults.buttonColors(MainRed),
               contentPadding = PaddingValues(vertical = 12.dp, horizontal = 10.dp),
               shape = MaterialTheme.shapes.large,
               modifier = md.width(250.dp)
           ) {
               Row(
                   horizontalArrangement = Arrangement.SpaceBetween,
                   verticalAlignment = Alignment.CenterVertically, modifier = md.fillMaxWidth()
               ) {
                   Text(text = "Adicionar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                   Text(text = toBrazilianCurrency(total), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
               }
           }
       }
    }

}