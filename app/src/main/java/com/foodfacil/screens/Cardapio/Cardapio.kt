package com.foodfacil.screens.Cardapio

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.components.SalgadoItem
import com.foodfacil.dataclass.SalgadoResponseDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cardapio(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData
) {


    val TAG = "CARDAPIO"
    val print = Print(TAG);

    val userToken by storeUserData.getToken.collectAsState(initial = "")

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
                Spacer(md.height(50.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()

                    ) {
                    salgadosList.value.forEach{salgado->
                        SalgadoItem(md = md, salgado = salgado, navController = navController)
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
        .padding(top = 40.dp, start = 20.dp, end = 20.dp)){
        Text(text = "Confira nosso cardápio completo abaixo", color = Color.Black,
            fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
    }

}
