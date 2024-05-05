package com.foodfacil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.foodfacil.dataclass.PedidoDoUsuarioResponseDto

@Composable
fun PedidoItem(
    pedido: PedidoDoUsuarioResponseDto,
    md: Modifier
) {
    Column() {
        DataDeCriacaoText(pedido.createdAt)
        Card(modifier = md.fillMaxWidth()) {
            SalgadosDentroDePedidoItem(pedido, md)
        }
    }
}
