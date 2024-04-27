package com.foodfacil.screens.EscolherFormaDePagamento

import NavigationBarColor
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.braziliancurrencyvisualtransformation.BrazilianCurrencyVisualTransformation
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.PagarNoDinheiroContainer
import com.foodfacil.components.PixContainer
import com.foodfacil.components.WarnModal
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.classes.Keyboard
import com.gamestate.classes.keyboardAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun EscolherFormaDePagamento(
    navController: NavHostController,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData,
    userViewModel: UserViewModel,
    chartViewModel: ChartViewModel
) {
    //voce tem um grande amor
    //tico mia
    val md = Modifier
    val print = Print()

    val totalPrice = chartViewModel.priceTotal.collectAsState()
    val taxaEntrega = 2f

    val total by remember {
        mutableFloatStateOf(totalPrice.value + taxaEntrega)
    }
    print.log("tota**l", total)

    val pixSelecionadoSnap by userViewModel.pixSelecionado.collectAsState(initial = true)
    val valorApagarEmDinheiroSnap by userViewModel.valorApagarEmDinheiro.collectAsState(initial = 0f)
    val precisaDeTrocoSnap by userViewModel.precisaDeTroco.collectAsState(initial = true)

    NavigationBarColor(color = Color.White)

    val metodoDePagamentoSelecionado =
        storeUserData.getMetodoDePagamento.collectAsState(initial = "")

    print.log(metodoDePagamentoSelecionado.value)

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    var dinheiroInput by remember {
        mutableStateOf("")
    }

    val dinheiroInputOnChange: (text: String) -> Unit = { text ->
        // Remove todos os caracteres que não são números
        val onlyNumbers = text.filter { it.isDigit() }
        dinheiroInput = onlyNumbers
    }

    var warnModalAtivo by remember {
        mutableStateOf(false)
    }

    if (warnModalAtivo)
        WarnModal(
            title = "Você deve reservar um dinheiro maior ou equivalente ao seu pedido",
            confirmTitle = "Entendi",
            onConfirmClick = {warnModalAtivo = false},
            toogleAddDialogVisibility = {warnModalAtivo = false}
        )

    var confirmou by remember {
        mutableStateOf(false)
    }

    val handleConfirmar:(precisaDeTrocoState:Boolean)->Unit = {precisa->
        scope.launch {
            userViewModel.handlePrecisaDeTroco(precisa)
            userViewModel.handlePagaNoDinheiro(dinheiroInput.toFloat())
            confirmou = true
            sheetState.hide()
        }
    }


    /*
        CODIGO QUE LIDA COM O CLIQUE NO BOTAO DE VOLTAR
     */
    val handleClickVoltar: () -> Unit = {
        if(inputEhValido(dinheiroInput)){
            print.log("total",total)
            print.log("trocoInput boolean", dinheiroInput.toFloat()/100 < total)
            print.log("trocoInput", dinheiroInput.toFloat()/100)
        }

        //selecionou dinheiro e confirmou alguma coisa
        if(!pixSelecionadoSnap && confirmou) {
            if(inputEhValido(dinheiroInput) ){
               if(dinheiroInput.toFloat() < total){
                   scope.launch {
                       warnModalAtivo = true
                       sheetState.show()
                   }
               }
                else{
                    navController.popBackStack()
               }
            }
        }
        //selecionou dinheiro mas nao confirmou nada
        if(!pixSelecionadoSnap && !confirmou){
            userViewModel.handleEscolhePix(true)
        }
    }

    val currentOnBack by rememberUpdatedState(handleClickVoltar)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleClickVoltar()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = true
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
    /*
          CODIGO QUE LIDA COM O CLIQUE NO BOTAO DE VOLTAR
     */

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet(dinheiroInput, dinheiroInputOnChange, scope,handleConfirmar) },
        sheetBackgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = md.padding(horizontal = 20.dp)
        ) {
            BackButtonWithTitle(
                navController = navController,
                title = "Forma de pagamento",
                marginRight = 0.dp
            )
            Spacer(md.height(50.dp))
            PixContainer(
                trailingIconVisible = false,
                borderColor = if (pixSelecionadoSnap) MainRed else Color.LightGray
            ) {
                userViewModel.handleEscolhePix(true)
            }

            Spacer(modifier = md.height(15.dp))
            PagarNoDinheiroContainer(borderColor = if (!pixSelecionadoSnap) MainRed else Color.LightGray) {
                scope.launch {
                    userViewModel.handleEscolhePix(false)

                    sheetState.show()
                }
            }
        }
    }
}

fun isNumeric(input: String): Boolean {
    return input.matches("\\d*\\.?\\d+".toRegex())
}

fun inputEhValido(input: String):Boolean{

    if(isNumeric(input)){
        return input.toFloat() / 100 >= 1f
    }
    return false
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BottomSheet(
    trocoInput: String,
    trocoInputOnChange: (text: String) -> Unit,
    scope: CoroutineScope,
    handleConfirmar: (precisaDeTrocoState: Boolean) -> Unit
) {

    val keyboard by keyboardAsState()
    val state = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            text = "Vai precisar de troco?",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Digite o valor que você vai pagar em dinheiro, para que o entregador leve o troco para você.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
            fontSize = 15.sp
        )
        Spacer(Modifier.height(30.dp))
        TextField(
            value = trocoInput,
            onValueChange = trocoInputOnChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = BrazilianCurrencyVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Black
            ),
            textStyle = MaterialTheme.typography.h6,
        )

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = {handleConfirmar(true)},
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 17.dp),
            colors = ButtonDefaults.buttonColors(
                MainRed
            ),
            shape = RoundedCornerShape(10.dp),
            enabled =  inputEhValido(trocoInput)
        ) {
            Text(
                text = "Confirmar",
                style = MaterialTheme.typography.h6,
                fontSize = 15.sp,
                color = if(inputEhValido(trocoInput))Color.White else Color(0xff606060)
            )
        }
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = { handleConfirmar(false) },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 17.dp),
            border = BorderStroke(1.dp, Color.Red),
            colors = ButtonDefaults.outlinedButtonColors(
                Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Não preciso de troco",
                style = MaterialTheme.typography.h6,
                fontSize = 15.sp,
                color = MainRed
            )
        }

        if (keyboard == Keyboard.Opened) {
            scope.launch {
                state.animateScrollTo(300)
            }
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .padding(bottom = 300.dp)
            )
        }
    }
}