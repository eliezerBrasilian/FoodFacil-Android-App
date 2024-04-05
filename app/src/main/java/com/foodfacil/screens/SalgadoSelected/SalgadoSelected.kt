package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.foodfacil.dataClass.Salgado
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.PinkSalgadoSelected
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.simpletext.SimpleText

@Composable
fun SalgadoSelected(
    navController: NavHostController,
    id: String?,
    salgadosViewModel: SalgadosViewModel,
    acompanhamentosViewModel: AcompanhamentosViewModel,
    paddingValues: PaddingValues
) {
    val md = Modifier
    val print = Print("SALGADOSELECTED")

    val salgadoSelected = remember {
        mutableStateOf<Salgado?>(null)
    }
    val acompanhamentos = remember {
        mutableStateListOf<String>()
    }

    val priceFormated = remember {
        mutableStateOf("")
    }

    LaunchedEffect(null) {
        print.log("id", id)
        val founded = salgadosViewModel.findSalgado(id)

        print.log(founded)
        salgadoSelected.value = founded
        priceFormated.value = "R$ ${founded!!.priceInOffer}"

        val acompanhamentosList = acompanhamentosViewModel.getAcompanhamentosList()

        print.log("acompanhamentos", acompanhamentosList)

        acompanhamentos.clear()
        acompanhamentos.addAll(acompanhamentosList)

    }

    Scaffold(md.padding(paddingValues)) { pv ->
        Surface(md.padding(pv), color = PinkSalgadoSelected
        ) {
            Column(
                md.padding(top = 20.dp).background(Color.White)) {
                Top(md, navController, salgadoSelected)
                CurrentSalgadoDetails(md, salgadoSelected, priceFormated)
                MonteSeuPedido(md = md)
                Spacer(md.height(15.dp))
                LazyColumn(md
                        .weight(1f)
                        .fillMaxWidth().padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(acompanhamentos){
                        ItemWithCheckBox(text = it, onClick = {isActive->
                            print.log("clicked",isActive)
                        })
                    }
                }
              //  Box(md.height(50.dp).background(Color.Yellow).fillMaxWidth().navigationBarsPadding())

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

@Composable
fun BackIcon(md: Modifier, navController: NavHostController){
    Box(
        md
            .fillMaxWidth()
            .background(color = PinkSalgadoSelected)
            .padding(start = 15.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = MainYellow,
            modifier = md.clickable { navController.popBackStack() })
    }
}
@Composable
fun MonteSeuPedido(md: Modifier) {
    Box(
        md
            .background(Color(0xffF1F1F1))
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SimpleText("Monte seu pedido:", fontWeight = "bold", fontSize = 15)
            SimpleText(
                "Escolha de 1 até 5 opções",
                fontSize = 15,
                color = Color((0xff555353))
            )
        }
    }

}