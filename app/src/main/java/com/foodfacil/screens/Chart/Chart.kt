package com.foodfacil.screens.Chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.Centralize
import com.foodfacil.components.ChartItem
import com.foodfacil.components.Circle
import com.foodfacil.components.Line
import com.foodfacil.components.Rectangle
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.Salgado
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.PinkSalgadoSelected
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
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val listaAdicionaisOnSnapshot by chartViewModel.adicionais.observeAsState();

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

    val totalPrice = chartViewModel.getTotalPrice()

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
        }, bottomBar = { RowVerCarrinho(totalPrice = totalPrice, onClick = clickedOnFinalizarPedido) })

    { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(pv)
            ) {
                if (cvm.isNullOrEmpty()) {
                    SimpleText("Seu carrinho está vazio")
                } else {
                    Top(navController = navController, cvm, incrementOnClick, decrementOnClick)
                    Line()
                    MainContent(salgadosViewModel.adicionais.value, addAdicionalNoCarrinho, adicionaisSelected)
                }
            }
        }
    }
}

@Composable
private fun Top(
    navController: NavHostController, cvm: List<Salgado>?,
    incrementOnClick: (salgadoId: String) -> Unit = { s: String -> },
    decrementOnClick: (salgadoId: String) -> Unit = { s: String -> }
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .heightIn(max = 250.dp)
    ) {
        //lazycolumn
        if (!cvm.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(cvm) { salgado ->
                    ChartItem(
                        salgado,
                        increment = incrementOnClick, decrement = decrementOnClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MainContent(
    adicionais: List<AdicionalDto>,
    addAdicionalNoCarrinho: (item: AdicionalDto) -> Unit,
    adicionaisSelected: SnapshotStateList<String>
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(1f)) {
        Spacer(modifier = Modifier.height(30.dp))
        SimpleText("Compre também", fontWeight = "bold", fontSize = 18, marginLeft = 20)

        Spacer(modifier = Modifier.height(20.dp))
        LazyRow {
            items(adicionais) { adicional ->
                val isSelected = if(adicionaisSelected.contains(adicional.id))true else false
                ItemAdicional(adicional,addAdicionalNoCarrinho,isSelected)
            }
        }
    }
}

@Composable
fun RowVerCarrinho(totalPrice: Float, onClick: () -> Unit = {}) {
    val md = Modifier

    if (totalPrice == 0f)
        Box(
            md
                .height(0.dp)
                .fillMaxWidth()
        ) {}
    else
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = md
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column {
                SimpleText("Total sem a entrega", fontSize = 15, color = Color.DarkGray)
                SimpleText("R$ $totalPrice", fontSize = 16)
            }
            Button(
                onClick = onClick,
                modifier = md.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    MainRed
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                shape = RoundedCornerShape(11.dp)
            ) {
                SimpleText("Finalizar Pedido", fontWeight = "bold", color = Color.White)
            }
        }
}

@Composable
fun ItemAdicional(
    adicional: AdicionalDto,
    addAdicionalNoCarrinho: (item: AdicionalDto) -> Unit,
    isSelected: Boolean,
) {
    val md = Modifier
    Box(
        modifier = md
            .padding(15.dp)
            .width(150.dp)
            .height(230.dp)
            .background(PinkSalgadoSelected, RoundedCornerShape(12.dp))
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Centralize {
                Rectangle(adicional.imagem)
            }

            Column(md.padding(start = 20.dp)) {
                Text(text = adicional.titulo, color = Color.Black, fontSize = 17.sp, maxLines = 1)
                SimpleText(adicional.descricao)
            }
            Centralize {
                Circle(md, MainYellow, 70.dp, hasElevation = true, onClick = {
                    addAdicionalNoCarrinho(
                        adicional
                    )
                }) {

                    Icon(imageVector = if (isSelected) Icons.Filled.Close else  Icons.Filled.Add, contentDescription = null, md.size(60.dp))
                }
            }
        }
    }
}
