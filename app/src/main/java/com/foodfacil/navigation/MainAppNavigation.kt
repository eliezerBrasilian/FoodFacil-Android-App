package com.foodfacil.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import com.foodfacil.graphs.HomeNavGraph
import com.foodfacil.enums.BottomBarScreen
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.foodfacil.components.BottomBar
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.enums.NavigationScreens
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainAppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
    ) {

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavGraph( navController, authViewModel, userViewModel)
    }
}

