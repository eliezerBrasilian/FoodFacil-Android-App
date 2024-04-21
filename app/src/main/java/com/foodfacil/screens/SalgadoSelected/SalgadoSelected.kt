package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.dataclass.Acompanhamento
import com.foodfacil.dataclass.Salgado
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.PinkSalgadoSelected
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel

@Composable
fun SalgadoSelected(
    navController: NavHostController,
    id: String?,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel
) {
    val md = Modifier
    val print = Print("CHARTVIEWMODEL")

    val precoProduto = remember {
        mutableFloatStateOf(0f)
    }

    val salgadoSelected = remember {
        mutableStateOf<Salgado?>(null)
    }

    val acompanhamentosReceived = remember {
        mutableStateListOf<Acompanhamento>()
    }

    val checkboxesStates = remember{
        mutableStateListOf<Acompanhamento>()
    }

    val observacaoInput = remember{
        mutableStateOf("")
    }

    val onChangeObservacaoInput:(text:String)->Unit = {
        if(it.length <= 141) observacaoInput.value = it
    }

    LaunchedEffect(null) {
        //busca salgados
        print.log("id", id)
        val founded = salgadosViewModel.findSalgado(id)

        print.log("founded", founded)

        if(founded != null){
            print.log(founded)
            salgadoSelected.value = founded

            precoProduto.floatValue =  if(founded.inOffer) founded.priceInOffer else founded.price

            //busca acompanhamentos
            val acompanhamentosList = founded.acompanhamentos
            print.log("acompanhamentos carregados", acompanhamentosList)

            acompanhamentosReceived.clear()
            acompanhamentosReceived.addAll(acompanhamentosList)
        }
    }

    val addSalgadoIntoChart:()->Unit = {
        val acompanhamentosSelecionados = checkboxesStates.toList()
        print.log("acompanhamentos",acompanhamentosSelecionados)

        salgadoSelected.value?.let { salgado->
            salgadoSelected.value = salgado.copy(
                acompanhamentos = (acompanhamentosSelecionados),
                amount = 1,
                observacao = observacaoInput.value.trim()
            )
        }

        print.log("salgadoSelected",salgadoSelected.value)
        chartViewModel.addSalgadoToChart(salgadoSelected.value)
        navController.popBackStack()
    }

    Scaffold(md.padding(paddingValues),
        bottomBar = { ButtonAddItemWithPrice(total = precoProduto.floatValue, addSalgadoIntoChart)}) { pv ->
        Surface(md.padding(pv), color = PinkSalgadoSelected
        ) {
            Column(
                md.background(Color.White)) {
                TopSalgadoSelected(md, navController, salgadoSelected)
                CurrentSalgadoDetails(md, salgadoSelected,
                    if(salgadoSelected.value?.inOffer == true) salgadoSelected.value?.priceInOffer else salgadoSelected.value?.price)
                MonteSeuPedido(md = md)
                Column(modifier = md
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .verticalScroll(rememberScrollState())) {
                    Spacer(md.height(15.dp))
                    acompanhamentosReceived.forEach {
                            acompanhamento->
                        Column {
                            Spacer(modifier = md.height(5.dp))
                            Row(modifier =Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = acompanhamento.name, color = Color(0xff4C4C4C),
                                    fontWeight = FontWeight.Normal, fontSize = 16.sp)
                                Checkbox(checked = checkboxesStates.contains(acompanhamento),
                                    onCheckedChange = {
                                    if(it){
                                        checkboxesStates.add(acompanhamento)
                                    }else{
                                        checkboxesStates.remove(acompanhamento)
                                    }
                                }, colors = androidx.compose.material.CheckboxDefaults.colors(checkedColor = MainRed))
                            }
                            Spacer(modifier = md.height(5.dp))
                            Line()
                        }
                    }
                    Spacer(md.height(18.dp))
                    Observacao(observacaoInput.value,onChangeObservacaoInput)
                    Spacer(md.height(20.dp))
                }
            }
        }
    }
}


