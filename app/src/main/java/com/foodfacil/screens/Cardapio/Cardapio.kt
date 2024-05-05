package com.foodfacil.screens.Cardapio

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.components.RowVerCarrinho
import com.foodfacil.dataclass.CardapioItem
import com.foodfacil.dataclass.Salgado
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainGray
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.simpletext.SimpleText

@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cardapio(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues,
) {

    val print = Print();

    print.log("combosList", salgadosViewModel.combosList())
    print.log("salgadosList", salgadosViewModel.salgados.value)

    val md = Modifier
    val loading = salgadosViewModel.loading.observeAsState(initial = true)
    val totalSalgadosNoCarrinho = chartViewModel.getTotalSalgados()
    val totalPrice = chartViewModel.getTotalPrice()

    Scaffold(
        md.padding(paddingValues), topBar = { CardapioTopBar() },
        bottomBar = { RowVerCarrinho(
            totalPrice = totalPrice,
            amount = totalSalgadosNoCarrinho,
        ) {
            navController.navigate(NavigationScreens.CHART)
        } },
        containerColor = Color.White
    ) { pV ->
        Surface(
            modifier = md
                .padding(pV)
                .fillMaxSize(), color = Color.White
        ) {

            if(loading.value){
                Box(modifier = md.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MainYellow,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = md.width(50.dp)
                    )
                }
            }

            else{
                CardapioContent(salgadosViewModel, md, navController)
            }
        }
    }
}

@Composable
private fun CardapioContent(
    salgadosViewModel: SalgadosViewModel,
    md: Modifier.Companion,
    navController: NavHostController
) {
    if (salgadosViewModel.salgadosNoCopoList().isEmpty() &&
        salgadosViewModel.risoleList().isEmpty() &&
        salgadosViewModel.coxinhaList().isEmpty() &&
        salgadosViewModel.pastelList().isEmpty() &&
        salgadosViewModel.hotDogList().isEmpty() &&
        salgadosViewModel.combosList().isEmpty() &&
        salgadosViewModel.congeladoList().isEmpty() &&
        salgadosViewModel.batataRostiList().isEmpty()
    ) {
        Column(
            modifier = md.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Não temos salgados no momento, mas fique ligado(a) :) no app",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            md
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Column(md.padding(horizontal = 10.dp)) {
                Spacer(md.height(20.dp))

                if (salgadosViewModel.salgadosNoCopoList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "SALGADOS NO COPO",
                        salgadosViewModel.salgadosNoCopoList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.risoleList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "RISOLES",
                        salgadosViewModel.risoleList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.coxinhaList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "COXINHAS",
                        salgadosViewModel.coxinhaList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.pastelList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "PASTÉIS",
                        salgadosViewModel.pastelList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.hotDogList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "HOT-DOGs",
                        salgadosViewModel.hotDogList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.combosList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "COMBOS",
                        salgadosViewModel.combosList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.congeladoList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "CONGELADOS",
                        salgadosViewModel.congeladoList(),
                        navController
                    )
                }

                Spacer(md.height(10.dp))

                if (salgadosViewModel.batataRostiList().isNotEmpty()) {
                    CardapioItemList(
                        md,
                        "BATATAS-ROSTI",
                        salgadosViewModel.batataRostiList(),
                        navController
                    )
                }

            }
        }
    }
}

@Composable
private fun CardapioItemList(
    md: Modifier.Companion,
    titulo: String,
    list: List<Salgado>,
    navController: NavHostController
) {
    Column {
        Text(
            titulo,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(md.height(27.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            list.forEach { salgado ->
                CardapioItem(md = md, salgado = salgado, navController = navController)
            }
        }
    }
}


@Composable
fun CardapioTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(MainGray),
        contentAlignment = Alignment.Center
    ) {
        SimpleText("Cardápio", color = MainRed, fontSize = 17)
    }

}
