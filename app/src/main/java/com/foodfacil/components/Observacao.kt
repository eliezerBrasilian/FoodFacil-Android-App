package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Observacao(value: String, onChangeObservacaoInput: (text: String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        ObservacaoTopRow(value = value)
        ObservacaoCaixaDeTexto(value, onChangeObservacaoInput)
    }
}