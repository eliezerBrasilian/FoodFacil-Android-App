package com.foodfacil.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodfacil.dataclass.PedidoDoUsuarioResponseDto

@Composable
fun SalgadosDentroDePedidoItem(
    pedido: PedidoDoUsuarioResponseDto,
    md: Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = md.fillMaxWidth().padding(horizontal = 10.dp)
        ) {
        pedido.salgados.forEach { salgado ->
            SalgadoItemDentroDePedido(salgado, md)
        }
        PedidoChao(pedido.status, pedido.pagamentoStatus)
    }
}