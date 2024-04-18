package com.foodfacil.screens.Chart

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.ChartTop
import com.foodfacil.components.ItemAdicional
import com.foodfacil.components.Line
import com.foodfacil.components.RowFinalizarCarrinho
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.simpletext.SimpleText

@Composable
fun ChartScreen(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel
) {

    val adicionaisSelected = remember{
        mutableStateListOf<String>()
    }

    val clickedOnFinalizarPedido:()->Unit = {
        navController.navigate(NavigationScreens.FINALIZAR_PEDIDO)
       /* val data = PaymentData("abc123","eliezerBrasilian","12345678911")
        coroutine.launch {
            val response = makePayment(data)

            if(response == "Pagamento aprovado"){
                Toast.makeText(context,"Seu pagamento foi aprovado",Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    val tag = "CHART"
    val print = Print(tag)

    val cvm by chartViewModel.chartList.observeAsState()

    val incrementOnClick: (salgadoId: String) -> Unit = { salgadoId ->
        print.log("cvm", cvm)
        chartViewModel.increment(salgadoId)
    }

    val decrementOnClick: (salgadoId: String) -> Unit = { salgadoId ->
        chartViewModel.decrement(salgadoId)
    }

    val addAdicionalNoCarrinho:(item:AdicionalDto)->Unit = {
        if(!adicionaisSelected.contains(it.id)){
            chartViewModel.addItemAdicional(it)
            adicionaisSelected.add(it.id)
        }

        else {
            chartViewModel.removeItemAdicional(it.id)
            adicionaisSelected.remove(it.id)
        }
    }

    val md = Modifier
    Scaffold(modifier = md
        .padding(paddingValues)
        .fillMaxSize(),
        topBar = {
            BackButtonWithTitle(
                navController = navController,
                title = "Carrinho de compras"
            )
        },
      //  bottomBar = {Box(modifier = md.height(0.dp))}
        bottomBar = { RowFinalizarCarrinho(
            onClick = clickedOnFinalizarPedido,
            total = chartViewModel.getTotalPrice(),
            text = "Total com a entrega"
        ) }
        //
        )

    { pv ->
        Surface(
            md
                .padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .fillMaxSize()
            ) {
                if (cvm.isNullOrEmpty()) {
                    SimpleText("Seu carrinho está vazio")
                } else {
                    ChartTop(cvm, incrementOnClick, decrementOnClick)
                    Line()
                    MainContent(salgadosViewModel.adicionais.value,
                        addAdicionalNoCarrinho, adicionaisSelected,
                        chartViewModel = chartViewModel,
                        onClick = clickedOnFinalizarPedido)

                }
            }
        }
    }
}

@Composable
private fun MainContent(
    adicionais: List<AdicionalDto>,
    addAdicionalNoCarrinho: (item: AdicionalDto) -> Unit,
    adicionaisSelected: SnapshotStateList<String>,
    chartViewModel: ChartViewModel,
    onClick: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    val marginInicial = 15.dp

    val currentMargin = if(scrollState.firstVisibleItemIndex == 0) marginInicial else 0.dp

    var cupomInput = remember{
        mutableStateOf("")
    }

    val onInputChange:(text:String)->Unit = {
        cupomInput.value = it.trim()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        SimpleText("Compre também", fontWeight = "bold", fontSize = 18, marginLeft = 20)

        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = currentMargin), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(adicionais) { adicional ->
                val isSelected = if(adicionaisSelected.contains(adicional.id))true else false
                ItemAdicional(adicional,addAdicionalNoCarrinho,isSelected)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        ChartCupom(cupomInput = cupomInput.value, onInputChange = onInputChange)
       // RowFinalizarCarrinho(cvm = chartViewModel, onClick = onClick)
        Spacer(modifier = Modifier.height(200.dp))
    }
}


@Composable
fun ChartCupom(cupomInput: String = "", onInputChange: (text: String) -> Unit = {}) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            ChartCupomLeft()
            Box(modifier = Modifier.clickable {  }){
                Text("Adicionar", fontSize = 18.sp, color = Color(0xffFF0303), fontWeight = FontWeight.SemiBold)
            }
        }
        TextField(value = cupomInput, onValueChange = onInputChange,
            placeholder = {
                Text(
                    "Insira o código do Cupom",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xffF8F8F8),
                cursorColor = MainYellow))
    }
}



@Composable
fun ChartCupomLeft(cuponsDisponiveisTotal:Int = 2){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(painter = painterResource(id = R.drawable.cupom), contentDescription = null, modifier = Modifier.size(30.dp))
        Column {
            Text("Cupom", fontSize = 17.sp, color = Color.Black, fontWeight = FontWeight.W600)
            Text("$cuponsDisponiveisTotal disponíveis para usar", fontSize = 13.sp, color = Color.DarkGray, fontWeight = FontWeight.Light)
        }
    }
}