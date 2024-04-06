package com.foodfacil.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Centralize(modifier: Modifier = Modifier.fillMaxWidth(), child: @Composable ()->Unit){
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        child()
    }
}