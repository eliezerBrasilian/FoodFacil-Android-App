package com.gamestate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LeftHiddenItem(size: Dp, isSelected: Boolean, icon: ImageVector,onClick: () -> Unit = {}){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        onClick()
    }) {
        if(isSelected){
            Indicator()
            Surface(shape = CircleShape, modifier = Modifier.size(size), color = Color(0xF0111111)){
                Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.fillMaxSize().padding(10.dp))
            }
        }
        else
            Box(modifier = Modifier.padding(start = 9.dp)){
                Surface(shape = CircleShape, modifier = Modifier.size(size), color = Color(0xF0111111)){
                    Icon(imageVector = icon, contentDescription = null, tint = Color.White,modifier = Modifier.fillMaxSize().padding(10.dp))
                }
            }
    }
}

@Composable
fun Indicator(){
    Surface(shape = RoundedCornerShape(10.dp), modifier = Modifier
        .height(40.dp)
        .width(9.dp),
        color = Color.Black){
    }

}
