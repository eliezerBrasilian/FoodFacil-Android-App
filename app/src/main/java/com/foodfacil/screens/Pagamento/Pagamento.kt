package com.foodfacil.screens.Pagamento
import NavigationBarColor
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.Centralize
import com.foodfacil.components.ChavePixContainerCopiaECola
import com.foodfacil.components.Circle
import com.foodfacil.components.CopiarCodigoPixButton
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.services.Print
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun Pagamento(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel,
    pedidoId: String?
) {

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val address = AddressDto(cidade = "", rua = "", numero = 0, bairro = "", complemento = "")

    val codigoPix = "duiedbeuibuiuifbrfrbfrfubfuibfuibfuifbuefbufbeubfberufbuerfbuibyewvuv77438edbdbwi--dedbwiudbdbub874--dededwdbwuibiu"

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

    NavigationBarColor(color = Color.White)

    var progress by remember { mutableStateOf(0f) }
    var isRunning by remember { mutableStateOf(true) }


        // Iniciar o temporizador
        LaunchedEffect(Unit) {
            val startTime = System.currentTimeMillis() // Obter o tempo atual em milissegundos

            while (progress < 1f) { // Até que o progresso seja 100%
                val currentTime = System.currentTimeMillis() // Obter o tempo atual em milissegundos
                val elapsedTime = (currentTime - startTime).toFloat() // Calcular o tempo decorrido
                progress = elapsedTime / 60000f // Atualizar o progresso (60.000 milissegundos em 1 minuto)
                delay(1000) // Aguardar 1 segundo
            }
            isRunning = false
        }


    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = { BackButtonWithTitle(navController = navController, title = "Confirme seu pagamento", marginRight = 0.dp) },
        bottomBar = {  })
    { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(md.height(20.dp))
                Centralize {
                    Circle(color = Color(0xffEAE5E5), size = 170.dp) {
                        Image(painter = painterResource(id = R.drawable.qr_code), contentDescription = null, md.size(120.dp))
                    }
                }
                Spacer(md.height(20.dp))
                Text(text = "Pedido aguardando pagamento",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black, fontSize = 18.sp,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(md.height(20.dp))

                Text(text = "Copie o código abaixo e cole no aplicativo de sua preferência para realizar o pagamento",
                    fontWeight = FontWeight.Normal,
                    color = Color.Black, fontSize = 16.sp,  textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                Spacer(md.height(15.dp))

                ChavePixContainerCopiaECola(codigoPix){
                    clickedOnFinalizarPedido()
                }

                Spacer(md.height(15.dp))

                LinearProgressIndicator(
                    progress = if (isRunning) progress else 1f, // Se o temporizador está em execução, use o progresso atual
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(md.height(15.dp))
                CopiarCodigoPixButton(onClick = clickedOnFinalizarPedido)
            }
        }
    }
}

