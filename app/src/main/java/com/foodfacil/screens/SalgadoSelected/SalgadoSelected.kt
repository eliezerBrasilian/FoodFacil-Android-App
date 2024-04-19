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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.R
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
        mutableStateListOf<Acompanhamento>()
    }

    val observacaoInput = remember{
        mutableStateOf("")
    }

    val onChangeObservacaoInput:(text:String)->Unit = {
        observacaoInput.value = it
    }

    LaunchedEffect(null) {
        //busca salgados
        print.log("id", id)
        val founded = salgadosViewModel.findSalgado(id)

        if(founded != null){
            print.log(founded)
            salgadoSelected.value = founded

            val price = if(founded.inOffer) founded.priceInOffer else founded.price
            total.value = price

            //busca acompanhamentos
            val acompanhamentosList = founded.acompanhamentos
            print.log("acompanhamentos carregados", acompanhamentosList)

            acompanhamentosReceived.clear()
            acompanhamentosReceived.addAll(acompanhamentosList)
            //checkboxesStates.add("Salgados Sortidos")
        }

    }

    val addSalgadoIntoChart:()->Unit = {
        val acompanhamentosSelecionados = checkboxesStates.toList()
        print.log("acompanhamentos",acompanhamentosSelecionados)

        salgadoSelected.value?.let { salgado->
            salgadoSelected.value = salgado.copy(
                acompanhamentos = (acompanhamentosSelecionados) as List<Acompanhamento>,
                price = total.value,
                newPriceAux = total.value,
                amount = 1
            )
        }

        print.log("salgadoSelected",salgadoSelected.value)
        chartViewModel.addSalgadoToChart(salgadoSelected.value)
        navController.popBackStack()
    }

    Scaffold(md.padding(paddingValues), bottomBar = { ButtonAddItemWithPrice(total = total.value, addSalgadoIntoChart)}) { pv ->
        Surface(md.padding(pv), color = PinkSalgadoSelected
        ) {
            Column(
                md.background(Color.White)) {
                TopSalgadoSelected(md, navController, salgadoSelected)
                CurrentSalgadoDetails(md, salgadoSelected, priceFormated)
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


                    /*    ItemWithCheckBox(text = acompanhamento.name, onClick = {isActive->
                            print.log("clicked",isActive)
                            print.log("acompanhamento",isActive)

                            if(isActive) {
                                // total.value += acompanhamento.precoPorUnidade
                                checkboxesStates.add(acompanhamento.name)
                            }
                            else {
                                //desmarcou
                                //  total.value -= acompanhamento.precoPorUnidade
                                checkboxesStates.remove(acompanhamento.name)
                            }
                            print.log("total",total.value)
                        }, isActive = esteItemJaEstaMarcado(acompanhamento.name))*/
                    }
                    Spacer(md.height(18.dp))
                    Observacao(observacaoInput.value,onChangeObservacaoInput)
                    Spacer(md.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun Observacao(value: String, onChangeObservacaoInput: (text: String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        ObservacaoTopRow(value = value)
        ObservacaoCaixaDeTexto(value,onChangeObservacaoInput)
    }
}


@Composable
fun ObservacaoTopRow(value: String) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            //left
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                LocalImage(imageResource = R.drawable.message, size = 17.dp)
                Text(text = "Alguma observação?", color = Color(0xff4C4C4C),
                    fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Text(text = "${value.length}/140", color = Color(0xff4C4C4C),
                fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
}

@Composable
fun ObservacaoCaixaDeTexto(value: String, onChangeObservacaoInput: (text: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        placeholder = { Text(text = "Escreva sua obervação aqui...", fontSize = 17.sp, color = Color.LightGray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xfff1f1f1),
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color(0xffBCBABA),
            focusedIndicatorColor = Color(0xffBCBABA)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        value = value,
        onValueChange = onChangeObservacaoInput,
        shape = RoundedCornerShape(10.dp))
}