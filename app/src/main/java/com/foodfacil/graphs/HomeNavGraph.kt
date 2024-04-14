package com.foodfacil.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.foodfacil.components.SalgadoSelected
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.BottomBarScreen
import com.foodfacil.enums.Graph
import com.foodfacil.screens.Chart.ChartScreen
import com.foodfacil.screens.FinalizarPedido.FinalizarPedido
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.foodfacil.screens.Home.Home
import com.foodfacil.screens.Pedidos
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.screens.Profile.Profile
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.screens.Cardapio.Cardapio
import com.foodfacil.screens.Pagamento.Pagamento

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData,) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route,
        modifier = Modifier.padding(0.dp).background(Color.White),
        builder = {
            composable(BottomBarScreen.Home.route) {
                Home(navController, authViewModel, userViewModel, salgadosViewModel,chartViewModel ,paddingValues, storeUserData)
            }
            composable(BottomBarScreen.Cardapio.route) {
                Cardapio(navController, authViewModel, userViewModel, salgadosViewModel,chartViewModel ,paddingValues, storeUserData)
            }

            detailsNavGraph(navController = navController, salgadosViewModel,
                paddingValues,chartViewModel, userViewModel)

           // chartNavGraph(navController,salgadosViewModel,acompanhamentosViewModel,paddingValues, chartViewModel)

            composable(BottomBarScreen.Pedidos.route) {
                Pedidos(navController, salgadosViewModel, paddingValues)
            }
            composable(BottomBarScreen.Perfil.route) {
                Profile(navController, authViewModel,userViewModel, paddingValues,storeUserData)
            }
        }
    )
}

fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel,
    userViewModel: UserViewModel
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route + "/{id}",
        //DetailsScreen.Information.route
    ) {

        composable(route = DetailsScreen.Information.route + "/{id}",
            arguments = listOf(navArgument(name = "id"){type = NavType.StringType})
        ) {route->
            val id = route.arguments?.getString("id")

            SalgadoSelected(navController, id,salgadosViewModel,
                paddingValues,chartViewModel)
        }

        composable(route = NavigationScreens.CHART){
            ChartScreen(navController,salgadosViewModel,
                paddingValues,chartViewModel)
        }

        composable(route = NavigationScreens.FINALIZAR_PEDIDO) {
            FinalizarPedido(navController, paddingValues,userViewModel, chartViewModel)
        }

        composable(route = NavigationScreens.PAGAMENTO) {
            Pagamento(navController, paddingValues,userViewModel, chartViewModel)
        }

        /*composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }*/
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}


/*
        composable(route = BottomBarScreen.Home.route) {
            ScreenContent(
                name = BottomBarScreen.Home.route,
                onClick = {
                    navController.navigate(com.foodfacil.enums.Graph.DETAILS)
                }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            ScreenContent(
                name = BottomBarScreen.Settings.route,
                onClick = { }
            )
        }
    }
}

*/