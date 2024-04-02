package com.foodfacil

import android.annotation.SuppressLint
import android.os.Build
import com.foodfacil.viewModel.AuthViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.foodfacil.ui.theme.FoodFacilTheme
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.viewModel.UserViewModel
import com.gamestate.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodFacilTheme(darkTheme = false) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White
                    ) {
                        val authViewModel: AuthViewModel = viewModel()
                        val userViewModel: UserViewModel = viewModel()
                        AppNavigation(authViewModel,userViewModel)
                    }
                }
            }
        }
    }
}

