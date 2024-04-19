package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.foodfacil.dataclass.Salgado
import com.simpletext.SimpleText
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Minus
import compose.icons.fontawesomeicons.solid.Plus

@Composable
fun Contador(
    modifier: Modifier = Modifier,
    iconSize: Int = 40,
    iconSubtract: ImageVector = FontAwesomeIcons.Solid.Minus,
    iconAdd: ImageVector = FontAwesomeIcons.Solid.Plus,
    iconAddColor: Color = Color.Red,
    iconSubtractColor: Color = Color.Red,
    value: Int = 0,
    backgroundColor: Color = Color.Green,
    increment: (salgadoId: String, salgado:Salgado) -> Unit = { s,salg -> },
    decrement: (salgadoId: String,salgado:Salgado) -> Unit = { s, salg -> },
    salgadoId: String,
    salgado: Salgado,
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
                imageVector = iconSubtract,
                contentDescription = null,
                tint = iconSubtractColor,
                modifier = modifier.size(iconSize.dp).clickable(onClick = { decrement(salgadoId, salgado) })
            )
            SimpleText(value.toString(), fontWeight = "400", fontSize = 17)
            Icon(
                imageVector = iconAdd,
                contentDescription = null,
                tint = iconAddColor,
                modifier = modifier.size(iconSize.dp).clickable(onClick = { increment(salgadoId,salgado) })
            )
        }
    }
}