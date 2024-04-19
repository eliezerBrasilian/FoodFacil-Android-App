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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.dataclass.CardapioItem
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainGray
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.simpletext.SimpleText

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cardapio(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues,
) {

    val TAG = "CARDAPIO"
    val print = Print(TAG);



    val salgadosList = remember {
        mutableStateOf(salgadosViewModel.salgados.value)
    }

    val md = Modifier

    Scaffold(
        md.padding(paddingValues), topBar = { CardapioTopBar() },
        bottomBar = {Box(md.height(0.dp))},
        containerColor = Color.White
    ) { pV ->
        Surface(
            modifier = md
                .padding(pV)
                .fillMaxSize(), color = Color.White
        ) {
            Column(
                md
                    .padding(start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Column(md.padding(horizontal = 10.dp)) {
                    Spacer(md.height(30.dp))
                    Text(
                        "COMBOS",
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
                        salgadosViewModel.combosList().forEach { salgado ->
                            CardapioItem(md = md, salgado = salgado, navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CardapioTopBar(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .background(MainGray),
        contentAlignment = Alignment.Center){
        SimpleText("Cardápio", color = MainRed, fontSize = 17)
    }

}
