package com.foodfacil.navigation

import OnAuthLogin
import OnAuthSignUp
import android.annotation.SuppressLint
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
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.screens.Cardapio.Cardapio
import com.foodfacil.screens.Chart.ChartScreen
import com.foodfacil.screens.Cupons.Cupons
import com.foodfacil.screens.DadosDaConta.DadosDaConta
import com.foodfacil.screens.Endereco.Endereco
import com.foodfacil.screens.EscolherFormaDePagamento.EscolherFormaDePagamento
import com.foodfacil.screens.FinalizarPedido.FinalizarPedido
import com.foodfacil.screens.Home.Home
import com.foodfacil.screens.Login.Login
import com.foodfacil.screens.OnAuth.OnAuth
import com.foodfacil.screens.Pagamento.Pagamento
import com.foodfacil.screens.Pedidos
import com.foodfacil.screens.Profile.Profile
import com.foodfacil.screens.SignUp.Signup
import com.foodfacil.screens.Splash.Splash
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.CuponsViewModel
import com.foodfacil.viewModel.PedidosViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainAppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData,
    cuponsViewModel: CuponsViewModel,
    pedidosViewModel: PedidosViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
         startDestination = BottomBarScreen.Home.route,
        //startDestination = NavigationScreens.SPLASH,
        modifier = Modifier
            .padding(0.dp)
            .background(Color.White),
        builder = {

            composable(NavigationScreens.SPLASH) {
                Splash(
                    navController,
                    storeUserData
                ) { navController.navigate(NavigationScreens.ON_AUTH) }
            }
            composable(NavigationScreens.ON_AUTH) {
                OnAuth(navController)
            }
            composable(NavigationScreens.LOGIN) {
                Login(
                    navController = navController,
                    authViewModel = authViewModel,
                    paddingValues = paddingValues,
                    storeUserData = storeUserData
                )
            }
            composable(NavigationScreens.SIGN_UP) {
                Signup(
                    navController = navController,
                    authViewModel = authViewModel,
                    paddingValues = paddingValues,
                    storeUserData = storeUserData
                )
            }
            composable(NavigationScreens.ON_AUTH_SIGN_UP) {
                OnAuthSignUp(navController, authViewModel)
            }
            composable(NavigationScreens.ON_AUTH_LOGIN) {
                OnAuthLogin(navController, authViewModel)
            }

            composable(BottomBarScreen.Home.route) {
                Home(
                    navController,
                    salgadosViewModel,
                    chartViewModel,
                    paddingValues,
                    storeUserData
                )
            }
            composable(BottomBarScreen.Cardapio.route) {
                Cardapio(navController, salgadosViewModel, chartViewModel, paddingValues)
            }

            detailsNavGraph(
                navController = navController, salgadosViewModel,
                paddingValues, chartViewModel, userViewModel, storeUserData
            )

            // chartNavGraph(navController,salgadosViewModel,acompanhamentosViewModel,paddingValues, chartViewModel)

            composable(BottomBarScreen.Pedidos.route) {
                Pedidos(navController, paddingValues,pedidosViewModel,storeUserData)
            }
            composable(BottomBarScreen.Perfil.route) {
                Profile(navController, authViewModel, userViewModel, paddingValues, storeUserData)
            }
            composable(NavigationScreens.CUPONS) {
                Cupons(
                    nav = navController,
                    paddingValues = paddingValues,
                    cuponsViewModel = cuponsViewModel,
                    storeUserData = storeUserData
                )
            }

            composable(NavigationScreens.DADOS_DA_CONTA){
                DadosDaConta(paddingValues, storeUserData, navController)
            }

            composable(NavigationScreens.ENDERECO){
                Endereco(paddingValues, storeUserData, navController)
            }

        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController,
    salgadosViewModel: SalgadosViewModel,
    paddingValues: PaddingValues,
    chartViewModel: ChartViewModel,
    userViewModel: UserViewModel,
    storeUserData: StoreUserData,
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route + "/{id}",
        //DetailsScreen.Information.route
    ) {

        composable(
            route = DetailsScreen.Information.route + "/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { route ->
            val id = route.arguments?.getString("id")

            SalgadoSelected(
                navController, id, salgadosViewModel,
                paddingValues, chartViewModel
            )
        }

        composable(route = NavigationScreens.CHART) {
            ChartScreen(
                navController, salgadosViewModel,
                paddingValues, chartViewModel
            )
        }

        composable(NavigationScreens.ESCOLHER_FORMA_DE_PAGAMENTO) {
            EscolherFormaDePagamento(
                navController = navController,
                paddingValues = paddingValues, storeUserData = storeUserData,
                userViewModel = userViewModel,
                chartViewModel = chartViewModel
            )
        }

        composable(route = NavigationScreens.PAGAMENTO + "/{pedidoId}",
            arguments = listOf(navArgument(name="pedidoId"){type = NavType.StringType})
        ) {  backStackEntry->
            val pedidoId = backStackEntry.arguments?.getString("pedidoId")
            Pagamento(navController, paddingValues, userViewModel, chartViewModel, pedidoId)
        }

        composable(route = NavigationScreens.FINALIZAR_PEDIDO) {
            FinalizarPedido(
                navController, paddingValues, userViewModel, chartViewModel,
                storeUserData
            )
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