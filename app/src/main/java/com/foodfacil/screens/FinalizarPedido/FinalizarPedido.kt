package com.foodfacil.screens.FinalizarPedido

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.AddAddressDialog
import com.foodfacil.components.AddressRow
import com.foodfacil.viewModel.ChartViewModel
import com.simpletext.SimpleText
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.CustomIcon
import com.foodfacil.components.PixContainer
import com.foodfacil.dataclass.AddressResponseDto
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.utils.Toast


@Composable
fun FinalizarPedido(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val user = userViewModel.user.observeAsState(initial = null)

    var rua by remember {
        mutableStateOf<String?>("")
    }
    var bairro by remember {
        mutableStateOf<String?>("")
    }
    var complememto by remember {
        mutableStateOf<String?>("")
    }
    var cidade by remember {
        mutableStateOf("")
    }
    var numero by remember {
        mutableStateOf<String?>("")
    }

    LaunchedEffect(true) {
        val address = user.value?.addressResponseDto
        if(address == null){
            rua = ""
            numero = ""
            bairro = ""
            complememto = ""
        }else{
            rua = address.rua
            numero = address.numero
            bairro = address.bairro
            complememto = address.complemento
        }

    }

    val clickedOnFinalizarPedido: () -> Unit = {
        navController.navigate(NavigationScreens.PAGAMENTO)
    }

    val dialogIsVisible = remember {
        mutableStateOf(false)
    }

    val toogleDialogVisible: () -> Unit = {
        dialogIsVisible.value = !dialogIsVisible.value
    }

    val tag = "CHART"
    val print = Print(tag)

    val cvm by chartViewModel.chartList.observeAsState()
    print.log("cvm", cvm)

    val totalPrice = chartViewModel.getTotalPrice()
    val md = Modifier

    val handleAddAddress: () -> Unit = {
        val addressResponseDto = AddressResponseDto(
            cidade = "Brás Pires",
            complemento = complememto, bairro = bairro, rua = rua, numero = numero )

        userViewModel.addAddress(addressResponseDto)
        Toast(context).showToast("Endereço adicionado")
    }

    val handleEditAddress: () -> Unit = {
        toogleDialogVisible()
    }

    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = {
            BackButtonWithTitle(navController = navController, title = "Finalizar pedido")
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
                if (user.value?.addressResponseDto == null) {
                    Box(md.clickable { toogleDialogVisible() }) {
                        Row {
                            SimpleText("Adicionar endereço", fontWeight = "bold", fontSize = 20)
                            CustomIcon(Icons.Default.Add)
                        }
                    }

                } else {
                    AddressRow(addressResponseDto = user.value?.addressResponseDto, onClick = handleEditAddress)
                }
                if (dialogIsVisible.value)
                    AddAddressDialog(
                        rua.toString(), numero.toString(), bairro.toString(), complememto.toString(),
                        handleAddAddress = handleAddAddress,
                        toogleDialogVisible = toogleDialogVisible,
                        onChangeRua = {rua = it},
                        onChangeNumero = {numero = it},
                        onChangeBairro = {bairro = it},
                        onChangeComplemento = {complememto = it},
                    )
            }
        }
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
            .padding(vertical = 10.dp, horizontal = 20.dp), contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row(md.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DisplayTotalItems(
                    first = "Subtotal:",
                    second = "Taxa de entrega:",
                    third = "Valor total:"
                )
                DisplayTotalItems(
                    first = "R$ $subtotal",
                    second = "R$ $taxaEntrega",
                    third = "R$ $totalPrice"
                )
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
                SimpleText(
                    "Finalizar Compra",
                    fontWeight = "bold",
                    fontSize = 19,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
private fun DisplayTotalItems(first: String, second: String, third: String) {
    Column {
        SimpleText(first, fontSize = 18)
        SimpleText(second, fontSize = 18)
        SimpleText(third, fontWeight = "bold", fontSize = 21)
    }
}