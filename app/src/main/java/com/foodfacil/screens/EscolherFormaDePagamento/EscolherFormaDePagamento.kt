package com.foodfacil.screens.EscolherFormaDePagamento

import NavigationBarColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.PagarNoDinheiroContainer
import com.foodfacil.components.PixContainer
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import kotlinx.coroutines.launch

@Composable
fun EscolherFormaDePagamento(
    navController: NavHostController,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData){
    val md = Modifier

    NavigationBarColor(color = Color.White)
    val print = Print("EscolherFormaDePagamento")
    val metodoDePagamentoSelecionado = storeUserData.getMetodoDePagamento.collectAsState(initial = "")

    print.log(metodoDePagamentoSelecionado.value)

    val scope = rememberCoroutineScope()

   Scaffold(modifier = md
       .padding(paddingValues)
       .fillMaxSize()
       .background(Color.White),
        topBar = {
            BackButtonWithTitle(navController = navController, title = "Forma de pagamento", marginRight = 0.dp)
        })
    { pv ->
        Surface(
            md
                .padding(pv)
                .fillMaxSize(), color = Color.White) {
            Column(
                modifier = md.padding(horizontal = 20.dp)
            ) {
                Spacer(md.height(10.dp))
                PixContainer(trailingIconVisible = false, borderColor = if(metodoDePagamentoSelecionado.value == "PIX") MainRed else Color.LightGray){
                    scope.launch {
                        storeUserData.saveMetodoDePagamento("PIX")
                    }
                }

                Spacer(modifier = md.height(15.dp))
                PagarNoDinheiroContainer(borderColor = if(metodoDePagamentoSelecionado.value == "DINHEIRO") MainRed else Color.LightGray ){
                    scope.launch {

                        storeUserData.saveMetodoDePagamento("DINHEIRO")
                    }

                }
            }
        }
    }
}