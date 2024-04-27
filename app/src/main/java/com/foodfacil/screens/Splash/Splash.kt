package com.foodfacil.screens.Splash

import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.ui.theme.MainRed
import kotlinx.coroutines.delay


@Composable
fun Splash(
    nav: NavHostController,
    storeUserData: StoreUserData,
    onLogin: () -> Unit
) {
    val scale by remember {
        mutableStateOf(Animatable(0f))
    }

    val userToken by storeUserData.getToken.collectAsState(initial = "")

    val context = LocalContext.current as ComponentActivity
    LaunchedEffect(key1 = true, block = {
        //status bar
        context.enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(MainRed.toArgb(), MainRed.toArgb()))

        scale.animateTo(0.9f, animationSpec = tween(800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))

        delay(1500L)

         if (userToken != null && userToken!= "") {
             nav.navigate(NavigationScreens.HOME)
        }
         else {
           onLogin()
        }
    })

    Surface(modifier = Modifier.fillMaxSize(), color = MainRed) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .size(400.dp)
                    .scale(scale.value),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foodfacillogo_splash),
                    null,
                    modifier = Modifier.size(300.dp)
                )
            }
            Spacer(modifier = Modifier.padding(top = 30.dp))
        }

    }

}