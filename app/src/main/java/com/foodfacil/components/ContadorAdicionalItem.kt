package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.dataclass.Adicional
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Minus
import compose.icons.fontawesomeicons.solid.Plus

@Composable
fun ContadorAdicionalItem(
    modifier: Modifier = Modifier,
    iconSize: Dp = 40.dp,
    iconSubtract: ImageVector = FontAwesomeIcons.Solid.Minus,
    iconAdd: ImageVector = FontAwesomeIcons.Solid.Plus,
    iconAddColor: Color = Color.Red,
    iconSubtractColor: Color = Color.Red,
    containerHeight: Dp = 30.dp,
    containerWidth: Dp = 60.dp,
    borderRadius: Dp = 10.dp,
    value: Int = 0,
    backgroundColor: Color = Color.Green,
    increment: (adicional: Adicional) -> Unit = { a -> },
    decrement: (adicional: Adicional) -> Unit = { a -> },
    adicional: Adicional,
) {
    Box(
        modifier = modifier.background(backgroundColor, RoundedCornerShape(borderRadius))
            .height(containerHeight).width(containerWidth).padding(horizontal = 5.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = iconSubtract,
                contentDescription = null,
                tint = iconSubtractColor,
                modifier = modifier.size(iconSize).clickable(onClick = { decrement( adicional) })
            )
            Text(
                text = value.toString(),
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Icon(
                imageVector = iconAdd,
                contentDescription = null,
                tint = iconAddColor,
                modifier = modifier.size(iconSize).clickable(onClick = { increment(adicional) })
            )
        }
    }
}