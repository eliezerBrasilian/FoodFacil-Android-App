package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.foodfacil.ui.theme.PinkSalgadoSelected

@Composable
 fun RoundedImage(size: Dp = 90.dp, modifier: Modifier = Modifier, image:Int = 0, imageUrl:String? = null){
     Box(modifier =
     modifier.background(PinkSalgadoSelected,
         RoundedCornerShape(10.dp)).size(size),
         contentAlignment = Alignment.Center){
         if(imageUrl != null){
             AsyncImage(model = imageUrl,
                 contentDescription = null,
                 modifier = modifier.clip(RoundedCornerShape(10.dp)))
         }else{
             Image(
                 painter = painterResource(id = image),
                 contentDescription = null
             )
         }
     }
}