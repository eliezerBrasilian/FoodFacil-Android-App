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
import com.foodfacil.api.registraPedido
import com.foodfacil.components.AddAddressDialog
import com.foodfacil.components.AddressRow
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.MetodoDePagamentoColumn
import com.foodfacil.components.NaoPossuiEnderecoColumn
import com.foodfacil.components.ResumoDoPedidoItems
import com.foodfacil.components.RowFinalizarCarrinho
import com.foodfacil.components.TempoEstimadoDeEntregaRow
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.dataclass.Pedido
import com.foodfacil.dataclass.SimplesAdicional
import com.foodfacil.dataclass.SimplesSalgado
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.enums.PagamentoStatus
import com.foodfacil.enums.PedidoStatus
import com.foodfacil.enums.Plataforma
import com.foodfacil.enums.TipoDePagamento
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.utils.AppDateTime
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.utils.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch



@Composable
fun FinalizarPedido(
    navController: NavHostController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel,
    storeUserData: StoreUserData,
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val savedRua = storeUserData.getRua.collectAsState(initial = "")
    val savedBairro = storeUserData.getBairro.collectAsState(initial = "")
    val savedNumeroEndereco = storeUserData.getNumeroEndereco.collectAsState(initial = "")
    val savedComplemento = storeUserData.getComplemento.collectAsState(initial = "")
    val userId = storeUserData.getUid.collectAsState(initial = "")
    val token = storeUserData.getToken.collectAsState(initial = "")
    val email = storeUserData.getEmail.collectAsState(initial = "")

    val pixSelecionado = userViewModel.pixSelecionado.collectAsState()
    val valorApagarEmDinheiroSnap = userViewModel.valorApagarEmDinheiro.collectAsState()

    val print = Print()

    val totalPrice = chartViewModel.priceTotal.collectAsState(0f)
    val salgadosNoCarrinhoSnap = chartViewModel.chartList.observeAsState()
    val adicionaisNoCarrinhoSnap = chartViewModel.adicionais.observeAsState()

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


    val dialogIsVisible = remember {
        mutableStateOf(false)
    }

    val toogleDialogVisible: () -> Unit = {
        dialogIsVisible.value = !dialogIsVisible.value
    }

    val cvm by chartViewModel.chartList.observeAsState()
    val md = Modifier

    val handleAddAddress: () -> Unit = {
        val address = AddressDto(
            cidade = "Brás Pires",
            complemento = complememto.toString(), bairro = bairro.toString(), rua = rua.toString(), numero = numero.toString().toInt() )

        print.log("endereco digitado",address)
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


    val scope = rememberCoroutineScope()

    var dispositivoToken by remember{
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                print.log("FCM", "Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            dispositivoToken = token.toString()
            print.log("token________*******",dispositivoToken)
        })
    }


    val clickedOnFinalizarPedido: () -> Unit = {

        val simplesSalgadoList:MutableList<SimplesSalgado> = emptyList<SimplesSalgado>().toMutableList()
        salgadosNoCarrinhoSnap.value?.forEach{ salgado->
            simplesSalgadoList.add(SimplesSalgado(id = salgado.id, observacao = salgado.observacao,
                sabores = salgado.sabores,
                quantidade = salgado.amount))
        }

        val simplesAdicionaisList:MutableList<SimplesAdicional> = emptyList<SimplesAdicional>().toMutableList()
        adicionaisNoCarrinhoSnap.value?.forEach{ adicional->
            simplesAdicionaisList.add(SimplesAdicional(id = adicional.id, quantidade = adicional.amount))
        }

        val endereco = AddressDto(cidade = "Brás Pires", rua = rua.toString(), numero = numero.toString().toInt(), bairro = bairro.toString(), complemento = complememto.toString())

        //informacoes de pagamento
        val pagamentoEscolhido = if(pixSelecionado.value) TipoDePagamento.PIX else  TipoDePagamento.DINHEIRO
        val quantiaReservada = if(!pixSelecionado.value) valorApagarEmDinheiroSnap.value else 0f

        val userToken = token.value

        val pedido = Pedido(salgados = simplesSalgadoList,
            acompanhamentos = simplesAdicionaisList,
            endereco = endereco,
            pagamentoEscolhido = pagamentoEscolhido,
            quantiaReservada = quantiaReservada,
            plataforma = Plataforma.ANDROID,
            total = total,
            dispositivoToken = dispositivoToken,
            createdAt = AppDateTime().obterMilisegundos(),
            status = PedidoStatus.AGUARDANDO_PREPARO,
            pagamentoStatus = PagamentoStatus.AGUARDANDO_PAGAMENTO,
            userId = userId.value.toString(),
            userEmail = email.value.toString()
            )

        print.log("Pedido",pedido)
        scope.launch {
            registraPedido(token = userToken.toString(), pedido = pedido, onSuccess = {pedidoId->
                print.log("pedido id",pedidoId)
                navController.navigate(NavigationScreens.PAGAMENTO + "/$pedidoId")
            })
        }
    }


    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = {
            BackButtonWithTitle(navController = navController, title = "Finalizar pedido")
        },
        bottomBar = { RowFinalizarCarrinho(total = total, text = "Total com a entrega", onClick = clickedOnFinalizarPedido)})
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

                MetodoDePagamentoColumn(md,pixSelecionado.value){
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

