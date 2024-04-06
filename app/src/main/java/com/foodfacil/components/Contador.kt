package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Minus
import compose.icons.fontawesomeicons.solid.Plus

@Composable
fun Contador(modifier: Modifier = Modifier,
             iconColor: Color = Color.Red,
             iconSize:Int = 40,
             value: Int = 0,
             backgroundColor: Color = Color.Green
) {
    Box(
        modifier = modifier.background(backgroundColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp).width(70.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Minus,
                contentDescription = null,
                tint = iconColor,
                modifier = modifier.size(iconSize.dp)
            )
            SimpleText(value.toString(), fontWeight = "400", fontSize = 17)
            Icon(
                imageVector = FontAwesomeIcons.Solid.Plus,
                contentDescription = null,
                tint = iconColor,
                modifier = modifier.size(iconSize.dp)
            )
        }
    }
}