package com.gamestate.screens.SignUp


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.livedata.observeAsState
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
import com.gamestate.classes.Keyboard
import com.gamestate.classes.keyboardAsState
import com.gamestate.components.AuthButton
import com.gamestate.components.InputText
import com.gamestate.components.Logo
import com.gamestate.components.PasswordInput
import com.gamestate.enums.KeyboardTypes
import com.gamestate.enums.NavigationScreens
import com.foodfacil.viewModel.AuthViewModel
import com.simpletext.SimpleText
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")//para o meu metodo animateScroll funcionar
@Composable
fun Signup(navController: NavHostController, authViewModel: AuthViewModel) {
    var emailInput by remember {
        mutableStateOf("")
    }

    var nameInput by remember {
        mutableStateOf("")
    }

//    val isLoading by authViewModel.loading.observeAsState(false) // Observando o estado de loading
val isLoading = false
    var passwordInput by remember {
        mutableStateOf("")
    }

    val keyboard by keyboardAsState()

    Log.e("KeyboardCheck", "$keyboard")

    val state = rememberScrollState()

    LaunchedEffect(key1 = true){
        state.animateScrollTo(100)
    }
    val scope = rememberCoroutineScope()

    suspend fun animateScrool() {
        state.animateScrollTo(300)
    }

    val context = LocalContext.current

    val handleSignUp: ()->Unit = {
        scope.launch {
            try {
                authViewModel.createUserWithEmailAndPassword(emailInput, passwordInput,nameInput , context) {
                    navController.navigate(NavigationScreens.HOME)
                }
            } catch (e: Exception) {
                // Lidar com exceções, se necessário
                Log.e(TAG, "Erro durante o cadastro: ${e.message}")
            }
        }
    }


    Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier
            .padding(20.dp)
            .verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Column(modifier = Modifier.fillMaxWidth()) {
                SimpleText(
                    title = "Olá seja muito bem vindo",
                    fontSize = 25,
                    fontWeight = "bold"
                )
                SimpleText(title = "Ao Gamestate", fontSize = 22, fontWeight = "bold")
            }

            Logo()
            InputText(value = nameInput,"digite seu nome...",
                keyboardType =  KeyboardTypes.NORMAL.name,
            ){
                nameInput = it
            }
            InputText(value = emailInput,"digite seu email...",
                keyboardType =  KeyboardTypes.EMAIL.name,
            ){
                emailInput = it
            }
            PasswordInput(passwordInput,"crie uma senha...",
                KeyboardTypes.PASSWORD.name,
            ){
                passwordInput = it
            }
            AuthButton(title = "Cadastrar", fontWeight = "bold", onClick = handleSignUp, isLoading = isLoading)
            if(keyboard == Keyboard.Opened){
                scope.launch {
                    animateScrool()
                }
                Box(modifier = Modifier
                    .background(Color.Magenta)
                    .padding(bottom = 300.dp))
            }

        }

    }
}