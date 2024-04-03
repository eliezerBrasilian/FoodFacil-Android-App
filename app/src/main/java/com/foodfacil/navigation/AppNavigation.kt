package com.gamestate.navigation

import OnAuthLogin
import OnAuthSignUp
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gamestate.enums.NavigationScreens
import com.gamestate.screens.Home.Home
import com.foodfacil.screens.Login.Login
import com.foodfacil.screens.OnAuth.OnAuth
import com.gamestate.screens.SignUp.Signup
import com.foodfacil.screens.Splash.Splash

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationScreens.SPLASH) {
        composable(NavigationScreens.SPLASH) {
            Splash(navController)
        }
        composable(NavigationScreens.LOGIN) {
            Login(navController, authViewModel)
        }
        composable(NavigationScreens.SIGN_UP) {
            Signup(navController, authViewModel)
        }
        composable(NavigationScreens.HOME) {
            Home(navController, authViewModel, userViewModel)
        }
        composable(NavigationScreens.ON_AUTH) {
            OnAuth(navController)
        }
        composable(NavigationScreens.ON_AUTH_LOGIN) {
            OnAuthLogin(navController)
        }
        composable(NavigationScreens.ON_AUTH_SIGN_UP) {
            OnAuthSignUp(navController)
        }

    /*    composable(NavigationScreens.PROFILE) {
            Profile(
                nav = navController, userViewModel
            )
        }*/

    }
}