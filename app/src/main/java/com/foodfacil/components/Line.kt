package com.foodfacil.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Line(color:Color = Color.LightGray,height:Int = 1,width:Int = 0){
    val modifier: Modifier = Modifier

    if(width > 0){
        Box(modifier = modifier.border(width = 1.dp, color = color).height(height.dp).width(width.dp))
    }else{
        Box(modifier = modifier.border(width = 1.dp, color = color).height(height.dp).fillMaxWidth())
    }
}