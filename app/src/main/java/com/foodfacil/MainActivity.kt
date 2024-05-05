package com.foodfacil

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.graphs.RootNavigationGraph
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

class MainActivity : ComponentActivity() {
    private val print = Print()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                print.log("FCM", "Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            print.log(token.toString())
        })

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

                    RootNavigationGraph(rememberNavController(),
                        authViewModel, userViewModel, salgadosViewModel,
                        chartViewModel,storeUserData, cuponsViewModel,pedidosViewModel)
                }
            }
        }
    }
}

