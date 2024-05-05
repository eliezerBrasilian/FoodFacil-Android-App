package com.foodfacil.screens.Profile

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.ButtonLogout
import com.foodfacil.components.Line
import com.foodfacil.components.PerfilTop
import com.foodfacil.components.ProfileMenuItem
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Profile(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData
) {
    val md = Modifier

    val userPhoto by storeUserData.getPhoto.collectAsState(initial = "")
    val userName by storeUserData.getName.collectAsState(initial = "")
    val userToken by storeUserData.getToken.collectAsState(initial = "")
    val userId by storeUserData.getUid.collectAsState(initial = "")

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val goBackToAuthScreen = {
        navController.navigate(NavigationScreens.ON_AUTH)
    }

    val print = Print()

    LaunchedEffect(Unit) {
        print.log(userPhoto)
    }

    val logout:()->Unit = {
        print.log("logout")
        coroutineScope.launch {
            StoreUserData(context).clearAllData()
            goBackToAuthScreen()
        }
    }

    Column(modifier = md
        .padding(paddingValues)
        .fillMaxSize()
        .background(Color.White)) {
        PerfilTop(md = md, userViewModel.user.value?.name, userPhoto,userName,userToken,userId, storeUserData)
        Line()

        ProfileMenuItem(md = md, title = "Dados da conta",
            icon = Icons.Default.Person, iconImage = R.drawable.user_perfil){
            navController.navigate(NavigationScreens.DADOS_DA_CONTA)
        }
        Line()

        ProfileMenuItem(md = md, title = "Cupons",
            icon = Icons.Default.Person, iconImage = R.drawable.coupons){
            navController.navigate(NavigationScreens.CUPONS)
        }
        Line()

        ProfileMenuItem(md = md, title = "Endereço",
            icon = Icons.Default.Person, iconImage = R.drawable.location){
            navController.navigate(NavigationScreens.ENDERECO)
        }
        Line()
        ButtonLogout(logout)
    }
}
