package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.foodfacil.dataclass.Salgado
import com.foodfacil.ui.theme.PinkSalgadoSelected

@Composable
fun TopSalgadoSelected(md: Modifier, navController: NavHostController, salgadoSelected: MutableState<Salgado?>) {
    Box(
        md
            .height(180.dp)
            .fillMaxWidth()
            .background(color = PinkSalgadoSelected),
    ) {
        AsyncImage(
            model = salgadoSelected.value?.imageRetangular,
            contentDescription = null,
            modifier = md.fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Box(md.fillMaxWidth().padding(top = 20.dp, start = 5.dp)) {
            BackIcon(navController = navController)
        }
    }
    }