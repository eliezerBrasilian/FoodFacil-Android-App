package com.foodfacil.screens.Login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.AuthButton
import com.foodfacil.components.BackIcon
import com.foodfacil.components.LocalImage
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.AuthViewModel
import com.gamestate.classes.Keyboard
import com.gamestate.classes.keyboardAsState
import com.gamestate.utils.Toast
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")//para o meu metodo animateScroll funcionar
@Composable
fun Login(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData
) {
    var emailInput by remember {
        mutableStateOf("teste1@gmail.com")
    }
    var passwordInput by remember {
        mutableStateOf("123456")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val print = Print("AUTHVIEWMODEL")

    val isLoading by authViewModel.loading.observeAsState(false)

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
        authViewModel.login(
            emailInput.trim(),
            passwordInput.trim(), onSuccess =
            { token: String, userId: String, name: String, profilePhoto: String? ->
                scope.launch {
                    print.log("handleLogin OK.....")
                    storeUserData.saveName(name)
                    storeUserData.saveToken(token)
                    storeUserData.saveUid(userId)
                    storeUserData.saveEmail(emailInput.trim())

                    navController.navigate(NavigationScreens.HOME)
                }
            }, onError = { message->
                Toast(context).showToast(message)
            }
        )
    }
    val md = Modifier
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), color = Color.White
    )  {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(state),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(modifier = md.fillMaxWidth()) {
                BackIcon(
                    md = Modifier,
                    navController = navController,
                    icon = Icons.Default.KeyboardArrowLeft
                )
            }
            LocalImage(imageResource = R.drawable.pastel, size = 70.dp)
            Spacer(md.height(35.dp))

            OutlinedTextField(
                modifier = md.fillMaxWidth(),
                placeholder = { Text(text = "Email", fontSize = 17.sp, color = Color.LightGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xfff1f1f1),
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = MainYellow,
                    focusedIndicatorColor = MainYellow
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                maxLines = 1,
                value = emailInput,
                onValueChange = { emailInput = it })

            OutlinedTextField(
                modifier = md.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Crie uma senha",
                        fontSize = 17.sp,
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xfff1f1f1),
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = MainYellow,
                    focusedIndicatorColor = MainYellow
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        imageVector = if (passwordVisible.value) FeatherIcons.EyeOff else FeatherIcons.Eye,
                        contentDescription = null,
                        tint = MainRed,
                        modifier =
                        md
                            .size(20.dp)
                            .clickable { passwordVisible.value = !passwordVisible.value }
                    )
                },

                maxLines = 1,
                value = passwordInput,
                onValueChange = { passwordInput = it })

            AuthButton(
                title = "Login",
                fontWeight = "bold",
                onClick = handleLogin,
                isLoading = isLoading,
                backgroundColor = MainRed
            )

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