package com.foodfacil.screens.OnAuth

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.foodfacil.components.Carrousel
import com.foodfacil.R
import com.foodfacil.components.CustomButton
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.ui.theme.YellowText
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simpletext.SimpleText

@SuppressLint("InlinedApi", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
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

    Surface(md.fillMaxSize(),
        color = MainRed) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Carrousel(
                imagesResources = listOf(
                    R.drawable.salgados_img_on_auth,
                    R.drawable.salgados_img_on_auth,
                    R.drawable.salgados_img_on_auth
                ),
                imageActiveColor = MainYellow,
                imageInactiveColor = Color.White,
                imageHeight = 320.dp,
                isCircle = true, dotWidth = 10.dp)

            Spacer(md.height(20.dp))

            Text(text = "Os salgados mais\n gostosos",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.clickable { navController.navigate(NavigationScreens.ON_AUTH_LOGIN) })
            Spacer(md.height(20.dp))
            SimpleText(
                "Os salgados mais deliciosos e mais baratos\n" + "de Rio Paranaíba. Com o incrível nível de \n" + "excelência que você merece.",
                fontSize = 16,
                color = Color.White
            )
            Spacer(md.height(20.dp))
            Box(
                md
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                CustomButton(buttonModifier = md.fillMaxWidth(), text = "Criar uma conta",
                    textModifier = md.padding(vertical = 4.dp),
                    onClick = {navController.navigate(NavigationScreens.ON_AUTH_SIGN_UP)})
            }
            Spacer(md.height(20.dp))
            Text(text = "Fazer login",
                fontSize = 17.sp,
                color = YellowText,
                modifier = Modifier.clickable { navController.navigate(NavigationScreens.ON_AUTH_LOGIN) })
        }
    }
}