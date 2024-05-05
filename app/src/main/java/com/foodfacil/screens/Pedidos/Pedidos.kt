package com.foodfacil.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.foodfacil.components.PedidoItem
import com.foodfacil.components.SemPedidosContent
import com.foodfacil.components.TopPedidos
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.viewModel.PedidosViewModel
import com.foodfacil.viewModel.SalgadosViewModel

@Composable
fun Pedidos(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues,
    pedidosViewModel: PedidosViewModel,
    storeUserData: StoreUserData
) {
    val md = Modifier
    Column(
        md
            .padding(paddingValues)
            .background(Color.White)
    ) {
        TopPedidos(md)
        MainContent(md = md, pedidosViewModel, storeUserData)
    }
}

@Composable
private fun MainContent(
    md: Modifier, pedidosViewModel: PedidosViewModel, storeUserData: StoreUserData
) {

    val userToken = storeUserData.getToken.collectAsState("")
    val userId = storeUserData.getUid.collectAsState(initial = "")

    val pedidos = pedidosViewModel.pedidos.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        pedidosViewModel.getAllPedidos(userToken.value.toString(), userId.value.toString())
    }

    if (pedidos.value.isEmpty()) {
        SemPedidosContent(md)
    } else {
        Column(
            md
                .fillMaxSize()
                .padding(10.dp)) {
            Text(
                text = "Histórico",
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                fontSize = 17.sp
            )

            pedidos.value.forEach{
                pedido->
                PedidoItem(pedido, md)
            }
        }
    }
}
