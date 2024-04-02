package com.gamestate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Comment
import compose.icons.fontawesomeicons.solid.Share
import compose.icons.fontawesomeicons.solid.Users

@Composable
fun Footer(){
    val iconSize = Modifier.size(30.dp)
    Row(modifier = Modifier.height(40.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Left(iconSize)
        Icon( FontAwesomeIcons.Solid.Share, contentDescription = null, modifier = iconSize, tint = Color.DarkGray)
    }
}

@Composable
fun Left(iconSize: Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        Icon( FontAwesomeIcons.Solid.Users, contentDescription = null,modifier = iconSize, tint = Color.DarkGray)
        Icon( FontAwesomeIcons.Solid.Comment, contentDescription = null,modifier = iconSize, tint = Color.DarkGray)
    }
}