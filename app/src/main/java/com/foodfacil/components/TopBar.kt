package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChevronLeft

@Composable
fun TopBar(title:String){
    val mod = Modifier
    Row(modifier = mod.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(imageVector = FontAwesomeIcons.Solid.ChevronLeft, contentDescription = null, modifier = mod.height(20.dp).width(20.dp))
        SimpleText(title)
    }
}
