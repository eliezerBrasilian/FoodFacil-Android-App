package com.foodfacil.screens.Chart

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.Line
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.simpletext.SimpleText
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.Centralize
import com.foodfacil.dataClass.Salgado
import com.foodfacil.components.ChartItem
import com.foodfacil.components.Circle
import com.foodfacil.components.Rectangle
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.PinkSalgadoSelected

data class Adicional(val id:String,val imagem:Int, val titulo:String,val descricao:String)

@Composable
fun ChartScreen(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    acompanhamentosViewModel: AcompanhamentosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel
) {

    val tag = "CHART"
    val print = Print(tag)

    val cvm by chartViewModel.chartList.observeAsState()
    print.log("cvm",cvm)


    val totalPrice = chartViewModel.getTotalPrice()

    val adicionais = listOf(
        Adicional("1", R.drawable.refrigerente,"Cola-cola","1 litro"),
        Adicional("2", R.drawable.refrigerente,"Fanta uva","2,5 litros"),
        Adicional("3", R.drawable.refrigerente,"Guaraná Antártica","1 litro")
    )

    val incrementOnClick:(salgadoId:String)->Unit = {salgadoId->
        print.log("cvm",cvm)
        chartViewModel.increment(salgadoId)
    }
    val decrementOnClick:(salgadoId:String)->Unit = {salgadoId->
        chartViewModel.decrement(salgadoId)
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
        }) { pv ->
        Surface(md.padding(pv), color = Color.White) {
            Column(
                modifier = md
                    .padding(pv)
            ) {

                if (cvm.isNullOrEmpty()) {
                    SimpleText("Seu carrinho está vazio")
                } else {
                    Top(navController = navController, cvm, incrementOnClick, decrementOnClick )
                    Line()
                    MainContent(adicionais)
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
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        //lazycolumn
        if (!cvm.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp),
            ) {
                items(cvm) { salgado ->
                    ChartItem(salgado,
                        increment = incrementOnClick, decrement = decrementOnClick)
                }
            }

            /*Spacer(modifier = Modifier.height(60.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                SimpleText("Adicionar mais itens", color = MainRed, fontWeight = "400")
            }*/
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MainContent(adicionais: List<Adicional>) {
    Column {

        Spacer(modifier = Modifier.height(30.dp))
        SimpleText("Compre também", fontWeight = "bold", fontSize = 18, marginLeft = 20)

        Spacer(modifier = Modifier.height(20.dp))
        LazyRow {
            items(adicionais){adicional->
                ItemAdicional(adicional)
            }
        }
    }
}

@Composable
fun ItemAdicional(adicional: Adicional) {
    val md = Modifier
    Box(modifier = md
        .padding(15.dp)
        .width(150.dp)
        .height(230.dp)
        .background(PinkSalgadoSelected, RoundedCornerShape(12.dp))){
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Centralize {
                Rectangle(adicional.imagem)
            }

            Column(md.padding(start = 20.dp)) {
                Text(text = adicional.titulo, color = Color.Black, fontSize = 17.sp , maxLines = 1)
                SimpleText(adicional.descricao)
            }
            Centralize {
                Circle(md, MainYellow,70.dp, hasElevation = true) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, md.size(60.dp))
                }
            }
        }
    }
}

/*
@Composable
fun ChartScreen(chartViewModel: ChartViewModel){
    val cvm by chartViewModel.chartList.observeAsState()

    val incrementOnClick:(salgadoId:String)->Unit = {salgadoId->
        print.log("cvm",cvm)
        chartViewModel.increment(salgadoId)
    }

    if (!cvm.isNullOrEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp),
        ) {
            items(cvm) { salgado ->
                ChartItem(
                    salgado,
                    increment = incrementOnClick
                )
            }
        }
    }
}

@Composable
fun ChartItem(
    salgado: Salgado, modifier: Modifier = Modifier,
    increment: (salgadoId: String) -> Unit = { s: String -> },
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = "R$ " +  salgado.newPriceAux,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        )
    }

    Icon(
        imageVector = FontAwesomeIcons.Solid.Plus,
        contentDescription = null,
        tint = Color.Black,
        modifier = modifier.size(50.dp).clickable(onClick = { increment(salgado.id) })
    )
}
 */