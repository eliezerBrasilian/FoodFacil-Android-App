package com.foodfacil

import android.os.Build
import com.foodfacil.viewModel.AuthViewModel
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
import com.foodfacil.graphs.RootNavigationGraph
import com.foodfacil.ui.theme.FoodFacilTheme
import com.foodfacil.viewModel.AcompanhamentosViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel

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
                    val authViewModel: AuthViewModel = viewModel()
                    val userViewModel: UserViewModel = viewModel()
                    val salgadosViewModel: SalgadosViewModel = viewModel()
                    val acompanhamentosViewModel: AcompanhamentosViewModel = viewModel()
                    val chartViewModel:ChartViewModel = viewModel()
                    RootNavigationGraph(rememberNavController(),
                        authViewModel, userViewModel, salgadosViewModel, acompanhamentosViewModel,
                        chartViewModel)
                }
            }
        }
    }
}

