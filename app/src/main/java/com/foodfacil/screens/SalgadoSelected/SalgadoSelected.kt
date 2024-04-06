package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.dataClass.Acompanhamento
import com.foodfacil.dataClass.Salgado
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.PinkSalgadoSelected
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
@Composable
fun SalgadoSelected(
    navController: NavHostController,
    id: String?,
    salgadosViewModel: SalgadosViewModel,
    acompanhamentosViewModel: AcompanhamentosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel
) {
    val md = Modifier
    val print = Print("SALGADOSELECTED")

    val total = remember {
        mutableStateOf(0f)
    }

    val salgadoSelected = remember {
        mutableStateOf<Salgado?>(null)
    }

    val acompanhamentosReceived = remember {
        mutableStateListOf<Acompanhamento>()
    }

    val priceFormated = remember {
        mutableStateOf("")
    }

    val checkboxesStates = remember{
        mutableStateListOf<String>()
    }

    LaunchedEffect(null) {
        //busca salgados
        print.log("id", id)
        val founded = salgadosViewModel.findSalgado(id)

        print.log(founded)
        salgadoSelected.value = founded
        priceFormated.value = "R$ ${founded!!.priceInOffer}"

        total.value += founded.priceInOffer

        //busca acompanhamentos
        val acompanhamentosList = acompanhamentosViewModel.getAcompanhamentosList()
        print.log("acompanhamentos carregados", acompanhamentosList)

        acompanhamentosReceived.clear()
        acompanhamentosReceived.addAll(acompanhamentosList)
        checkboxesStates.add("Salgados Sortidos")
        total.value += 1f
    }

    val esteItemJaEstaMarcado: (acompanhamento:String)->Boolean = {acompanhamento->
        val existe =  checkboxesStates.contains(acompanhamento)
        print.log("checkboxesStates",checkboxesStates.contains(acompanhamento))
        print.log("existe",existe)
        existe
    }

    val addSalgadoIntoChart = {
        val acompanhamentosSelecionados = checkboxesStates.toList()
        print.log("acompanhamentos",acompanhamentosSelecionados)

        salgadoSelected.value?.let { salgado->
            salgadoSelected.value = salgado.copy(
                acompanhamentos = (acompanhamentosSelecionados) as List<Acompanhamento>,
                price = total.value,
                amount = 1
            )
        }

        print.log("salgadoSelected",salgadoSelected.value)
        chartViewModel.addSalgadoToChart(salgadoSelected.value)
    }


    Scaffold(md.padding(paddingValues)) { pv ->
        Surface(md.padding(pv), color = PinkSalgadoSelected
        ) {
            Column(
                md
                    .padding(top = 20.dp)
                    .background(Color.White)) {
                Top(md, navController, salgadoSelected)
                CurrentSalgadoDetails(md, salgadoSelected, priceFormated)
                MonteSeuPedido(md = md)
                Spacer(md.height(15.dp))
                LazyColumn(
                    md
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(acompanhamentosReceived){ acompanhamento->
                        ItemWithCheckBox(text = acompanhamento.name, onClick = {isActive->
                            print.log("clicked",isActive)

                            if(isActive) {
                                total.value += acompanhamento.precoPorUnidade
                                checkboxesStates.add(acompanhamento.name)
                            }
                            else {
                                //desmarcou
                                total.value -= acompanhamento.precoPorUnidade
                                checkboxesStates.remove(acompanhamento.name)

                            }
                            print.log("total",total.value)
                        }, isActive = esteItemJaEstaMarcado(acompanhamento.name))
                    }
                }
                ButtonAddItemWithPrice(total = total.value, addSalgadoIntoChart)
            }
        }
    }
}


@Composable
private fun Top(md: Modifier, navController: NavHostController, salgadoSelected: MutableState<Salgado?>) {
    Column(modifier = md.fillMaxWidth()
    ) {
       BackIcon(md,navController)
        Box(
            md
                .height(240.dp)
                .fillMaxWidth()
                .background(color = PinkSalgadoSelected),
            contentAlignment = Alignment.Center
        ) {
            salgadoSelected.value?.image?.let { painterResource(id = it) }
                ?.let {
                    Image(
                        painter = it,
                        contentDescription = null,
                        modifier = md.width(260.dp),
                        contentScale = ContentScale.Crop
                    )
                }
        }
    }
}


