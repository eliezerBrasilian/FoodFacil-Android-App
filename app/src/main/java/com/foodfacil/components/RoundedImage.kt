package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.foodfacil.R
import com.foodfacil.ui.theme.PinkSalgadoSelected

@Composable
 fun RoundedImage(size: Dp = 90.dp, modifier: Modifier = Modifier, image:Int){
     Box(modifier = modifier.background(PinkSalgadoSelected, RoundedCornerShape(10.dp)),
         contentAlignment = Alignment.Center){
         Image(
             painter = painterResource(id = image),
             contentDescription = null,
             modifier = modifier.size(size)
         )
     }

}