package com.foodfacil.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.utils.toBrazilianCurrency
import com.simpletext.SimpleText

@Composable
fun RowVerCarrinho(totalPrice: Float, amount: Int, onClick: () -> Unit = {}) {

    val scale = remember {
        mutableStateOf(Animatable(0f))
    }

    LaunchedEffect(totalPrice) {
        scale.value.animateTo(1f, animationSpec = tween(900, easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }))

    }

    val item = if (amount == 1) "item" else "items"

    val md = Modifier

    if (amount == 0)
        Box(
            md
                .height(0.dp)
                .fillMaxWidth()
        ) {}
    else
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = md
                .fillMaxWidth()
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .scale(scale.value.value)
        ) {
            Column {
                SimpleText("Total sem a entrega", fontSize = 15, color = Color.DarkGray)
                Row {
                    SimpleText(toBrazilianCurrency(totalPrice), fontSize = 16)
                    SimpleText("/ $amount $item", fontSize = 15, color = Color.DarkGray)
                }
            }
            Button(
                onClick = onClick,
                modifier = md.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    MainRed
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                shape = RoundedCornerShape(11.dp)
            ) {
                SimpleText("Ver carrinho", fontWeight = "bold", color = Color.White)
            }
        }


}