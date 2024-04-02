package com.foodfacil.screens.OnAuth

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.carrousel.Carrousel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.foodfacil.R
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.YellowText
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simpletext.SimpleText

@SuppressLint("InlinedApi", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun OnAuth(
    navController: NavHostController
) {

    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    val context = LocalContext.current as ComponentActivity

    DisposableEffect(systemUiController, useDarkIcons) {
        context.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(MainRed.toArgb(), MainRed.toArgb())
        )

        onDispose {}
    }

    val md = Modifier

    Surface(md.fillMaxSize(), color = MainRed) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Carrousel(imagesResources = listOf(
                R.drawable.salgados_img_on_auth,
                R.drawable.salgados_img_on_auth,
                R.drawable.salgados_img_on_auth),
                imageActiveColor = MainYellow,
                imageInactiveColor = Color.White
                )

            Spacer(md.height(20.dp))
            SimpleText(
                "Os salgados mais gostosos",
                fontSize = 24,
                fontWeight = "bold",
                color = Color.White
            )
            SimpleText(
                "Os salgados mais deliciosos e mais baratos\n" + "de Rio Paranaíba. Com o incrível nível de \n" + "excelência que você merece.",
                fontSize = 16,
                color = Color.White
            )
            Box(md.fillMaxWidth().padding(horizontal = 20.dp)){
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(MainYellow),
                    modifier = md.fillMaxWidth()) {
                    Box(md.padding(vertical = 5.dp)){
                        SimpleText("Criar uma conta", color = Color.White, fontSize = 17)
                    }
                }
            }

            SimpleText("Fazer login", fontSize = 17, fontWeight = "bold", color = YellowText)
        }
    }

}