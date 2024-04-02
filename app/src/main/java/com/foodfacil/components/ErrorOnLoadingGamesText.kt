package com.gamestate.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorOnLoadingGamesText(){
    // Tratar o caso em que os dados não foram carregados com sucesso
    // Mostrar uma mensagem de erro ou algo semelhante
    Text(
        text = "Erro ao carregar os jogos",
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center
    )
}