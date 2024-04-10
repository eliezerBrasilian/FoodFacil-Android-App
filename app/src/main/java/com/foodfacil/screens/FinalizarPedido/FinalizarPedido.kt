package com.foodfacil.screens.Chart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.api.makePayment
import com.foodfacil.viewModel.ChartViewModel
import com.simpletext.SimpleText
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.dataClass.Address
import com.foodfacil.dataClass.PaymentData
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun FinalizarPedido(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel
) {

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    
    val address = userViewModel.user.value.address
    
    val clickedOnFinalizarPedido: () -> Unit = {
        val data = PaymentData("abc123", "eliezerBrasilian", "12345678911")
        coroutine.launch {
            val response = makePayment(data)

            if (response == "Pagamento aprovado") {
                Toast.makeText(context, "Seu pagamento foi aprovado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val tag = "CHART"
    val print = Print(tag)

    val cvm by chartViewModel.chartList.observeAsState()
    print.log("cvm", cvm)

    val totalPrice = chartViewModel.getTotalPrice()
    val md = Modifier

    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = { BackButtonWithTitle(navController = navController, title = "Finalizar pedido")
        },
        bottomBar = { DisplayTotal(subtotal = totalPrice, onClick = clickedOnFinalizarPedido) })
    { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(horizontal = 20.dp)
            ) {
                SimpleText("Método de Pagamento", fontWeight = "bold", fontSize = 20)
                Spacer(md.height(10.dp))
                PixContainer()
                Spacer(md.height(15.dp))
                if(address == null){
                    SimpleText("Adicionar endereço", fontWeight = "bold", fontSize = 20)
                }else{
                    AddressRow(address = address)
                }
            }
        }
    }
}

@Composable
private fun PixContainer() {
    val md = Modifier
    Box(
        modifier = md
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color.Black, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.simbolopix), contentDescription = null, md.size(40.dp))
            Column {
                SimpleText("PIX", fontWeight = "bold", fontSize = 19)
                SimpleText("Aprovação imediata", fontWeight = "300", fontSize = 16, color = Color.DarkGray)
            }
            Image(painter = painterResource(id = R.drawable.selecionar), contentDescription = null, md.size(30.dp))
        }

    }
}

@Composable fun AddressRow(address: Address?){
    val md = Modifier
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Column {
            SimpleText("Endereço", fontWeight = "bold", fontSize = 19)
            SimpleText("Rua ${address?.rua}, ${address?.numero}...", fontWeight = "300", fontSize = 17)
        }
        Image(painter = painterResource(id = R.drawable.edit), contentDescription = null, md.size(35.dp))
    }
}
@Composable
private fun DisplayTotal(subtotal: Float, onClick: () -> Unit = {}) {
    val md = Modifier
    val taxaEntrega = 2.00f
    val totalPrice = subtotal + taxaEntrega

    Box(
        md
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp), contentAlignment = Alignment.Center){
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row(md.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DisplayTotalItems(first = "Subtotal:", second = "Taxa de entrega:", third = "Valor total:")
                DisplayTotalItems(first = "R$ $subtotal", second = "R$ $taxaEntrega", third = "R$ $totalPrice")
            }
            Button(
                onClick = onClick,
                modifier = md.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    MainRed
                ),
                contentPadding = PaddingValues(vertical = 15.dp),
                shape = RoundedCornerShape(11.dp)
            ) {
                SimpleText("Finalizar Pedido", fontWeight = "bold", fontSize = 19, color = Color.White)
            }
        }

    }
}

@Composable
private fun DisplayTotalItems(first:String,second:String,third:String){
    Column {
        SimpleText(first, fontSize = 18,)
        SimpleText(second, fontSize = 18)
        SimpleText(third, fontWeight = "bold", fontSize = 21)
    }
}