package com.foodfacil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.foodfacil.R

import com.foodfacil.enums.PagamentoStatus
import com.foodfacil.enums.PedidoStatus

@Composable
fun PedidoChao(status: PedidoStatus, pagamentoStatus: PagamentoStatus) {

    var icone by remember {
        mutableStateOf(R.drawable.pagamento_naoconfirmado)
    }
    var text by remember {
        mutableStateOf("Aguardando pagamento")
    }

    if(status == PedidoStatus.EM_PREPARO){
        icone = R.drawable.pedido_icone_selecionado
        text = "Estamos preparando seu pedido"
    }
    else if(status == PedidoStatus.FINALIZADO){
        icone = R.drawable.pedido_finalizado
        text = "Seu pedido está finalizado"
    }
    else if(status == PedidoStatus.SAIU_PARA_ENTREGA){
        icone = R.drawable.pedido_rotaentrega
        text = "Pedido saiu para entrega"
    }

    else if(status == PedidoStatus.CHEGOU_NO_ENDERECO){
        icone = R.drawable.pedido_finalizado
        text = "Seu pedido chegou no seu endereço"
    }

    Row(horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically){
        LocalImage(imageResource = icone, size = 10.dp)

        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = Color.Black,
            fontSize = 17.sp
        )
    }

}