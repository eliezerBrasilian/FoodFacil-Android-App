package com.foodfacil.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.foodfacil.graphs.HomeNavGraph
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.foodfacil.components.BottomBar
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.viewModel.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainAppNavigation(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    navController: NavHostController = rememberNavController(),
    storeUserData: StoreUserData,
    cuponsViewModel: CuponsViewModel,
) {

    Scaffold(
        modifier = Modifier.background(Color.White),

        bottomBar = { BottomBar(navController = navController) }, content = {paddingValues ->
            HomeNavGraph( navController, authViewModel, userViewModel,salgadosViewModel,
                chartViewModel, paddingValues, storeUserData,cuponsViewModel)

        }
    )
}

