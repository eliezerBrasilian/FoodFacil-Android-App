package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.PinkSalgadoSelected

@Composable
fun BackIcon(md: Modifier, navController: NavHostController){
    Box(md.padding(start = 15.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = MainYellow,
            modifier = md.clickable { navController.popBackStack() })
    }
}

