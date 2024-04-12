package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foodfacil.R
import com.foodfacil.dataClass.Address
import com.simpletext.SimpleText

@Composable
fun AddressRow(address: Address?, onClick: () -> Unit){
    val md = Modifier
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp), modifier = md.clickable { onClick() }
    ) {
        Column {
            SimpleText("Endereço", fontWeight = "bold", fontSize = 19)
            SimpleText(
                "Rua ${address?.rua}, ${address?.numero}...",
                fontWeight = "300",
                fontSize = 17
            )
        }
        Image(
            painter = painterResource(id = R.drawable.edit),
            contentDescription = null,
            md.size(35.dp)
        )
    }
}