package com.foodfacil.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.Graph
import com.foodfacil.navigation.MainAppNavigation
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(
    navController: NavHostController, authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    acompanhamentosViewModel: AcompanhamentosViewModel,
    chartViewModel: ChartViewModel,
    storeUserData: StoreUserData,) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController, authViewModel, userViewModel,storeUserData)
        composable(route = Graph.HOME) {
            MainAppNavigation(authViewModel = authViewModel, userViewModel = userViewModel,
                salgadosViewModel = salgadosViewModel,
                acompanhamentosViewModel = acompanhamentosViewModel,
                chartViewModel = chartViewModel, storeUserData = storeUserData)
        }
    }
}
