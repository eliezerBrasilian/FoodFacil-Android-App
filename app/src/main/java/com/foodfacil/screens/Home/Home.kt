package com.foodfacil.screens.Home

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.HomeHeader
import com.foodfacil.components.SalgadoItem
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.simpletext.SimpleText

@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues
) {
    val cA = LocalContext.current as ComponentActivity

    LaunchedEffect(key1 = true, block = {
        //status bar
        cA.enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(MainRed.toArgb(), MainRed.toArgb()))
    })

    val md = Modifier
    Scaffold(md.padding(paddingValues), topBar = { HomeHeader(md = md)},
        bottomBar = {Box(md.fillMaxWidth().height(0.dp))},
        containerColor = Color.White) { pV ->
        Surface(modifier = md
            .padding(pV)
            .fillMaxSize(), color = Color.White) {
            Column(md.padding(start = 10.dp, end = 10.dp).verticalScroll(rememberScrollState())) {
                Spacer(md.height(30.dp))
                SimpleText("Promoções Imperdíveis", fontSize = 22 , fontWeight = "400")
                Spacer(md.height(20.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        userScrollEnabled = false
                    ) {
                        items(salgadosViewModel.salgadosInOfferList()) {
                            SalgadoItem(md = md, salgado = it, navController = navController)
                        }
                    }

                Spacer(md.height(30.dp))
                SimpleText("Salgadinhos no Copo", fontSize = 22 , fontWeight = "400")
                Spacer(md.height(20.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(salgadosViewModel.salgadosNoCopoList()){
                        SalgadoItem(md = md, salgado = it, navController)
                    }
                }
            }
        }
    }
}