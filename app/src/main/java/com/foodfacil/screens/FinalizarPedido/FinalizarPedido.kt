package com.foodfacil.screens.FinalizarPedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.components.AddAddressDialog
import com.foodfacil.components.AddressRow
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.MetodoDePagamentoColumn
import com.foodfacil.components.NaoPossuiEnderecoColumn
import com.foodfacil.components.ResumoDoPedidoItems
import com.foodfacil.components.RowFinalizarCarrinho
import com.foodfacil.components.TempoEstimadoDeEntregaRow
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.utils.Toast
import kotlinx.coroutines.launch


@Composable
fun FinalizarPedido(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel,
    storeUserData: StoreUserData
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val savedRua = storeUserData.getRua.collectAsState(initial = "")
    val savedBairro = storeUserData.getBairro.collectAsState(initial = "")
    val savedNumeroEndereco = storeUserData.getNumeroEndereco.collectAsState(initial = "")
    val savedComplemento = storeUserData.getComplemento.collectAsState(initial = "")
    val userId = storeUserData.getUid.collectAsState(initial = "")
    val token = storeUserData.getToken.collectAsState(initial = "")

    val totalPrice = chartViewModel.priceTotal.collectAsState(0f)

    var rua by remember {
        mutableStateOf<String?>("")
    }
    var bairro by remember {
        mutableStateOf<String?>("")
    }
    var complememto by remember {
        mutableStateOf<String?>("")
    }

    var numero by remember {
        mutableStateOf<String?>("")
    }

    LaunchedEffect(true) {
        if(savedRua.value.isNullOrEmpty()){
            rua = ""
            numero = ""
            bairro = ""
            complememto = ""
        }else{
            rua = savedRua.value
            numero = savedNumeroEndereco.value
            bairro = savedBairro.value
            complememto = savedComplemento.value
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

    val md = Modifier

    val handleAddAddress: () -> Unit = {
        val address = AddressDto(
            cidade = "Brás Pires",
            complemento = complememto.toString(), bairro = bairro.toString(), rua = rua.toString(), numero = numero.toString().toInt() )

        userViewModel.addAddress(address, token.toString(), userId.value.toString()) {
            coroutine.launch {
                storeUserData.saveRua(rua.toString().trim())
                storeUserData.saveBairro(bairro.toString().trim())
                if(complememto.toString().trim().isEmpty()){
                    storeUserData.saveComplemento(complememto.toString().trim())
                }
                storeUserData.saveNumeroEndereco(numero.toString().trim())
                storeUserData.saveCidade("Brás Pires")
                Toast(context).showToast("Endereço adicionado")
                toogleDialogVisible()
            }
        }
    }

    val handleEditAddress: () -> Unit = {
        toogleDialogVisible()
    }

    val taxaEntrega = 2.00f
    val cupomFreteGratisAplicado = false
    val total = if(cupomFreteGratisAplicado)totalPrice.value else totalPrice.value + taxaEntrega

    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = {
            BackButtonWithTitle(navController = navController, title = "Finalizar pedido")
        },
        bottomBar = { RowFinalizarCarrinho(total = total, text = "Total com a entrega")})
    { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(horizontal = 20.dp)
            ) {

                TempoEstimadoDeEntregaRow()
                ResumoDoPedidoItems(subtotal = totalPrice.value)

                if(savedRua.value == ""){
                    NaoPossuiEnderecoColumn(onClick = toogleDialogVisible)
                }else{
                    Column(modifier = md.fillMaxWidth()){
                        Spacer(md.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween, modifier = md.fillMaxWidth()){
                            Text(
                                text = "Entregar no endereço", color = Color.Black,
                                fontSize = 17.sp, fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Trocar", color = MainRed,
                                fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = md.clickable { toogleDialogVisible() }
                            )
                        }

                        Spacer(md.height(10.dp))
                        AddressRow(rua = savedRua.value.toString(),
                            numero = savedNumeroEndereco.value.toString(),
                            bairro = savedBairro.value.toString(),
                            complemento = savedComplemento.value.toString()
                            )
                        Spacer(md.height(15.dp))
                    }
                }

                MetodoDePagamentoColumn(md){
                    navController.navigate(NavigationScreens.ESCOLHER_FORMA_DE_PAGAMENTO)
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

