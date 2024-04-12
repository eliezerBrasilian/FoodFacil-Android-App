package com.foodfacil.screens.Pagamento

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
import com.foodfacil.components.Centralize
import com.foodfacil.dataClass.Address
import com.foodfacil.dataClass.PaymentData
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun Pagamento(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel
) {

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    
    val address = userViewModel.user.value?.address

    val codigoQR = "duiedbeuibuiuifbrfrbfrfubfuibfuibfuifbuefbufbeubfberufbuerfbuibyewvuv77438edbdbwi--dedbwiudbdbub874--dededwdbwuibiu"

    val clickedOnFinalizarPedido: () -> Unit = {
        Toast.makeText(context, "Chave pix, copiada", Toast.LENGTH_SHORT).show()

        /*val data = PaymentData("abc123", "eliezerBrasilian", "12345678911")
        coroutine.launch {
            val response = makePayment(data)

            if (response == "Pagamento aprovado") {
                Toast.makeText(context, "Seu pagamento foi aprovado", Toast.LENGTH_SHORT).show()
            }
        }*/
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
        topBar = { BackButtonWithTitle(navController = navController, title = "Pix")
        },
        bottomBar = { CopiarCodigoPix(codigoQr = codigoQR, onClick = clickedOnFinalizarPedido) })
    { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(horizontal = 20.dp)
            ) {
                PedidogeradoRow(address = address)
                Spacer(md.height(20.dp))
                Centralize {
                    SimpleText("Pague com", fontWeight = "bold", fontSize = 24)
                }
                Spacer(md.height(15.dp))
                Row(md.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Image(painter = painterResource(id = R.drawable.simbolopix), contentDescription = null, md.size(35.dp))
                    SimpleText("PIX", fontSize = 24)
                }
                Spacer(md.height(15.dp))
                Column(verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = md.fillMaxWidth()){
                    QRCode()
                    SimpleText("Copie ou escaneie o QR CODE", fontWeight = "bold", fontSize = 22)
                    Centralize(modifier = Modifier.fillMaxWidth()) {
                        SimpleText("Ao copiar abra o seu aplicativo cadastrado no pixe realize o seu pagamento de forma rápida", fontSize = 20)
                    }

                }

            }
        }
    }
}

@Composable
private fun QRCode(){
    val md = Modifier
    Image(painter = painterResource(id = R.drawable.qr_code_fake), contentDescription = null, md.size(120.dp))
}

@Composable fun PedidogeradoRow(address: Address?){
    val md = Modifier
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Image(painter = painterResource(id = R.drawable.selecionar), contentDescription = null, md.size(35.dp))
        Column {
            SimpleText("Pedido Gerado", fontWeight = "bold", fontSize = 24)
            SimpleText("Agora você só precisa finalizar o pagamento", fontWeight = "300", fontSize = 16)
        }

    }
}
@Composable
private fun CopiarCodigoPix(codigoQr: String, onClick: () -> Unit = {}) {
    val md = Modifier

    Box(
        md
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp), contentAlignment = Alignment.Center){
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row(md.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SimpleText("Código Válido até", fontWeight = "bold", fontSize = 19)
                SimpleText("22 horas  e 50 minutos", color = Color.Red , fontWeight = "bold", fontSize = 19)
            }
            Button(
                onClick = onClick,
                modifier = md.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF2BE484)
                ),
                contentPadding = PaddingValues(vertical = 15.dp),
                shape = RoundedCornerShape(11.dp)
            ) {
                SimpleText("Copiar chave PIX", fontWeight = "bold", fontSize = 19, color = Color.White)
            }
        }

    }
}
