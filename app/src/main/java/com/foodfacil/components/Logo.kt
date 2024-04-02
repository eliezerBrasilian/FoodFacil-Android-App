package com.gamestate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foodfacil.R
@Composable
fun Logo(){
    Image(painter = painterResource(id = R.drawable.foodfacillogo_splash), contentDescription = null,
        modifier = Modifier.size(300.dp))
}