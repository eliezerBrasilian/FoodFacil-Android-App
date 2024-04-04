package com.foodfacil.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.foodfacil.enums.BottomBarScreen
import com.foodfacil.enums.Graph
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.enums.NavigationScreens
import com.gamestate.screens.Home.Home

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController,
                 authViewModel: AuthViewModel,
                 userViewModel: UserViewModel,) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = NavigationScreens.HOME
    ) {
        composable(NavigationScreens.HOME) {
            Home(navController, authViewModel, userViewModel)
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
        }*/
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
       /* composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
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
