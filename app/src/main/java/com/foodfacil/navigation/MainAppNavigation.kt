package com.foodfacil.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.foodfacil.graphs.HomeNavGraph
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.foodfacil.components.BottomBar
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainAppNavigation(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    acompanhamentosViewModel: AcompanhamentosViewModel,
    chartViewModel: ChartViewModel,
    navController: NavHostController = rememberNavController(),
) {

    val systemUiController = rememberSystemUiController()
    //val useDarkIcons = MaterialTheme.colors.isLight

    LaunchedEffect(true) {
        systemUiController.setNavigationBarColor(Color.White)
        systemUiController.navigationBarDarkContentEnabled = true
    }

    Scaffold(
        modifier = Modifier.background(Color.White),

        bottomBar = { BottomBar(navController = navController) }, content = {paddingValues ->
            HomeNavGraph( navController, authViewModel, userViewModel,salgadosViewModel,
                acompanhamentosViewModel,chartViewModel, paddingValues)

        }
    )
}

