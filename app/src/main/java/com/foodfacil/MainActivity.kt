package com.foodfacil

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.foodfacil.api.salvaTokenDoDispositivo
import com.foodfacil.components.BottomBar
import com.foodfacil.dataclass.TokenDoDispositivoRequestDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.navigation.MainAppNavigation
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.FoodFacilTheme
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.CuponsViewModel
import com.foodfacil.viewModel.PedidosViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FoodFacilTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val storeUserData = StoreUserData(context = applicationContext)
                    val authViewModel: AuthViewModel = viewModel()
                    val userViewModel: UserViewModel = viewModel()
                    val salgadosViewModel: SalgadosViewModel = viewModel()
                    val chartViewModel:ChartViewModel = viewModel()
                    val cuponsViewModel:CuponsViewModel = viewModel()
                    val pedidosViewModel:PedidosViewModel = viewModel()

                    val navController: NavHostController = rememberNavController()

                    val print = Print()

                    val scope = rememberCoroutineScope()

                    val userId = storeUserData.getUid.collectAsState(initial = "")
                    val userToken = storeUserData.getToken.collectAsState(initial = "")

                    LaunchedEffect(Unit) {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                print.log("FCM", "Fetching FCM registration token failed ${task.exception}")
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            print.log("token________*******",token.toString())

                                scope.launch {
                                    salvaTokenDoDispositivo(
                                        TokenDoDispositivoRequestDto(token = token, userId = userId.value.toString()),
                                        userToken = userToken.value.toString()
                                    );
                                }
                        })
                    }

                    Scaffold(
                        modifier = Modifier.background(Color.White),

                        bottomBar = { BottomBar(navController = navController) },
                        content = {paddingValues ->
                            MainAppNavigation( navController, authViewModel, userViewModel,salgadosViewModel,
                                chartViewModel, paddingValues, storeUserData,cuponsViewModel, pedidosViewModel)

                        }
                    )

                }
            }
        }
    }
}

