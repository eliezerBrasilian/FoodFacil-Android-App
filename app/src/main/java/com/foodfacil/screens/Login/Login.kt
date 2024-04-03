package com.foodfacil.screens.Login

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.viewModel.AuthViewModel
import com.gamestate.classes.Keyboard
import com.gamestate.classes.keyboardAsState
import com.gamestate.components.AuthButton
import com.gamestate.components.InputText
import com.gamestate.components.Logo
import com.gamestate.components.PasswordInput
import com.gamestate.enums.KeyboardTypes
import com.gamestate.enums.NavigationScreens
import com.simpletext.SimpleText
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")//para o meu metodo animateScroll funcionar
@Composable
fun Login(
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    var emailInput by remember {
        mutableStateOf("eliezerassuncaocustodio@gmail.com")
    }
    var passwordInput by remember {
        mutableStateOf("123456")
    }

    val TAG = "LOGIN"

    //val isLoading by authViewModel.loading.observeAsState(false) // Observando o estado de loading
    val isLoading = false


    val keyboard by keyboardAsState()

    Log.e("KeyboardCheck", "$keyboard")

    val state = rememberScrollState()

    LaunchedEffect(key1 = true) {
        state.animateScrollTo(100)

        navController.clearBackStack(NavigationScreens.SPLASH)
    }
    val scope = rememberCoroutineScope()

    suspend fun animateScrool() {
        state.animateScrollTo(300)
    }

    val context = LocalContext.current


    val handleLogin: () -> Unit = {
       /* scope.launch {
            try {
                authViewModel.login(emailInput, passwordInput, context) {
                    navController.navigate(NavigationScreens.HOME)
                }
            } catch (e: Exception) {
                // Lidar com exceções, se necessário
                Log.e(TAG, "Erro durante o login: ${e.message}")
            }
        }*/
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                SimpleText(
                    title = "Olá seja muito bem vindo",
                    fontSize = 25,
                    fontWeight = "bold"
                )
                SimpleText(title = "Ao Gamestate", fontSize = 22, fontWeight = "bold")
            }

            Logo()
            InputText(
                value = emailInput, "digite seu email...",
                keyboardType = KeyboardTypes.EMAIL.name,
            ) {
                emailInput = it
            }
            PasswordInput(
                passwordInput, "digite sua senha...",
                KeyboardTypes.PASSWORD.name,
            ) {
                passwordInput = it
            }
            AuthButton(
                title = "Login",
                fontWeight = "bold",
                onClick = handleLogin,
                isLoading = isLoading
            )
            Box(modifier = Modifier.clickable { navController.navigate(NavigationScreens.SIGN_UP) }) {
                SimpleText("Não possuo conta")
            }
            if (keyboard == Keyboard.Opened) {
                scope.launch {
                    animateScrool()
                }
                Box(
                    modifier = Modifier
                        .background(Color.Magenta)
                        .padding(bottom = 300.dp)
                )
            }

        }

    }
}