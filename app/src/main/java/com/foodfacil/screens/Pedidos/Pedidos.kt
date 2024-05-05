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
import com.foodfacil.enums.BottomBarScreen
import com.foodfacil.viewModel.PedidosViewModel

@Composable
fun Pedidos(
    nav: NavHostController,
    paddingValues: PaddingValues,
    pedidosViewModel: PedidosViewModel,
    storeUserData: StoreUserData
) {
    val userToken = storeUserData.getToken.collectAsState("")
    val userId = storeUserData.getUid.collectAsState(initial = "")
    val pedidos = pedidosViewModel.pedidos.collectAsState(emptyList())
    val md = Modifier

    LaunchedEffect(Unit) {
        pedidosViewModel.getAllPedidos(userToken.value.toString(), userId.value.toString())
    }

    val verCardapioClick:()->Unit = {
        nav.navigate(BottomBarScreen.Cardapio.route)
    }

    Column(
        md
            .padding(paddingValues)
            .background(Color.White)
    ) {
        TopPedidos(md)

        if (pedidos.value.isEmpty()) {
            SemPedidosContent(md, verCardapioClick)
        } else {
            Column(
                md
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Histórico",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    fontSize = 17.sp
                )

                pedidos.value.forEach { pedido ->
                    PedidoItem(pedido, md)
                }
            }
        }
    }
}
