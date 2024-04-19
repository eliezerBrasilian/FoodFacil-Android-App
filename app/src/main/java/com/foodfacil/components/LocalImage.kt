package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.foodfacil.R
@Composable
fun LocalImage(size: Dp = 200.dp, imageResource: Int = R.drawable.foodfacillogo_splash){
    Image(painter = painterResource(id = imageResource), contentDescription = null,
        modifier = Modifier.size(size))
}