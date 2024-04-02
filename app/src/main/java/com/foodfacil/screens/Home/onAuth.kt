package com.gamestate.screens.Home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun Home(
    navController: NavHostController, authViewModel: AuthViewModel, userViewModel: UserViewModel
) {
    val userAuth: AuthViewModel = viewModel()
    val context = LocalContext.current

    val md = Modifier
    Scaffold { paddingValues ->
        Surface(modifier = md.padding(paddingValues)) {

        }
    }
}