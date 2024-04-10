package com.foodfacil.graphs

import OnAuthLogin
import OnAuthSignUp
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.foodfacil.enums.Graph
import com.foodfacil.screens.Login.Login
import com.foodfacil.screens.OnAuth.OnAuth
import com.foodfacil.screens.Splash.Splash
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.foodfacil.enums.NavigationScreens

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.authNavGraph(navController: NavHostController, authViewModel: AuthViewModel,
                                 userViewModel: UserViewModel,) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = NavigationScreens.SPLASH
    ) {
        composable(NavigationScreens.SPLASH) {
            Splash(navController)
        }
        composable(NavigationScreens.ON_AUTH) {
            OnAuth(navController)
        }
        composable(NavigationScreens.LOGIN) {
           Login(navController, authViewModel)
        }
        composable(NavigationScreens.ON_AUTH_SIGN_UP) {
            OnAuthSignUp(navController,authViewModel)
        }
        composable(NavigationScreens.ON_AUTH_LOGIN) {
            OnAuthLogin(navController,authViewModel)
        }
    }
}

