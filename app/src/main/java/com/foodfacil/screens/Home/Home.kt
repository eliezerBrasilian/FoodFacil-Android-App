package com.foodfacil.screens.Home

import NavigationBarColor
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.FirebaseMessagingNotificationPermissionDialog
import com.foodfacil.components.HomeHeader
import com.foodfacil.components.SalgadoItem
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.foodfacil.enums.NavigationScreens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.simpletext.SimpleText

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    salgadosViewModel: SalgadosViewModel,
    chartViewModel: ChartViewModel,
    paddingValues: PaddingValues
) {
    val cA = LocalContext.current as ComponentActivity

    val cvm by chartViewModel.chartList.observeAsState(emptyList())
    val totalSalgadosNoCarrinho = chartViewModel.getTotalSalgados()
    val totalPrice = chartViewModel.getTotalPrice()

    val showNotificationDialog = remember { mutableStateOf(false) }

    // Android 13 Api 33 - runtime notification permission has been added
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    if (showNotificationDialog.value) FirebaseMessagingNotificationPermissionDialog(
        showNotificationDialog = showNotificationDialog,
        notificationPermissionState = notificationPermissionState
    )

    LaunchedEffect(key1=Unit){
        if (notificationPermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            Firebase.messaging.subscribeToTopic("Tutorial")
        } else showNotificationDialog.value = true
    }


    NavigationBarColor(color = MainYellow)

    /*    LaunchedEffect(key1 = true, block = {
            //status bar
            cA.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(Color.White.toArgb(), MainRed.toArgb()),
                //navigationBarStyle = SystemBarStyle.auto(Color.White.toArgb(), MainRed.toArgb())
                )

        })*/

    val md = Modifier
    Scaffold(
        md.padding(paddingValues), topBar = {
            HomeHeader(md = md, totalSalgadosNoCarrinho, onClickOnChartIcon = {
                navController.navigate(NavigationScreens.CHART)
            })
        },
        bottomBar = {
            RowVerCarrinho(
                totalPrice = totalPrice,
                amount = totalSalgadosNoCarrinho,
            ) {
                navController.navigate(NavigationScreens.CHART)
            }
        },
        containerColor = Color.White
    ) { pV ->
        Surface(
            modifier = md
                .padding(pV)
                .fillMaxSize(), color = Color.White
        ) {
            Column(
                md
                    .padding(start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(md.height(30.dp))
                SimpleText("Promoções Imperdíveis", fontSize = 22, fontWeight = "400")
                Spacer(md.height(20.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                    userScrollEnabled = false
                ) {
                    items(salgadosViewModel.salgadosInOfferList()) {
                        SalgadoItem(md = md, salgado = it, navController = navController)
                    }
                }

                Spacer(md.height(30.dp))
                SimpleText("Salgadinhos no Copo", fontSize = 22, fontWeight = "400")
                Spacer(md.height(20.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(salgadosViewModel.salgadosNoCopoList()) {
                        SalgadoItem(md = md, salgado = it, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun RowVerCarrinho(totalPrice: Float, amount: Int, onClick: () -> Unit = {}) {

    val scale = remember {
        mutableStateOf(Animatable(0f))
    }
    LaunchedEffect(totalPrice) {
        scale.value.animateTo(1f, animationSpec = tween(900, easing = {
            OvershootInterpolator(2f).getInterpolation(it)
        }))

    }

    val item = if (amount == 1) "item" else "items"

    val md = Modifier

    if (amount == 0)
        Box(
            md
                .height(0.dp)
                .fillMaxWidth()) {}
    else
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = md
                .fillMaxWidth()
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .scale(scale.value.value)
        ) {
            Column {
                SimpleText("Total sem a entrega", fontSize = 15, color = Color.DarkGray)
                Row {
                    SimpleText("R$ $totalPrice", fontSize = 16)
                    SimpleText("/ $amount $item", fontSize = 15, color = Color.DarkGray)
                }
            }
            Button(
                onClick = onClick,
                modifier = md.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    MainRed
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                shape = RoundedCornerShape(11.dp)
            ) {
                SimpleText("Ver carrinho", fontWeight = "bold", color = Color.White)
            }
        }


}