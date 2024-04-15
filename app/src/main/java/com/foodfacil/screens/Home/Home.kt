package com.foodfacil.screens.Home

import NavigationBarColor
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.FirebaseMessagingNotificationPermissionDialog
import com.foodfacil.components.HomeHeader
import com.foodfacil.components.RowVerCarrinho
import com.foodfacil.components.SalgadoItem
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.simpletext.SimpleText
import com.foodfacil.dataclass.SalgadoResponseDto
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
    paddingValues: PaddingValues,
    storeUserData: StoreUserData
) {

    val TAG = "HOME"
    val print = Print(TAG);

    val userToken by storeUserData.getToken.collectAsState(initial = "")

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


        salgadosViewModel.getAllSalgados_(userToken.toString())
        salgadosViewModel.getAllAdicionais_(userToken.toString())
    }

    NavigationBarColor(color = MainYellow)


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
                md.verticalScroll(rememberScrollState())
            ) {
                Spacer(md.height(30.dp))
                SimpleText("Promoções Imperdíveis", fontSize = 22, fontWeight = "400", marginLeft = 15)
                Spacer(md.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),

                ) {
                    salgadosViewModel.salgadosInOfferList().forEach{salgado->
                        SalgadoItem(md = md, salgado = salgado, navController = navController)
                    }
                }

                Spacer(md.height(30.dp))
                SimpleText("Batatas Rosti", fontSize = 22, fontWeight = "400", marginLeft = 15)
                Spacer(md.height(20.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    items(salgadosViewModel.batataRosti()){
                        SalgadoItem(
                            md = md.width(310.dp),
                            salgado = it,
                            navController = navController,
                            leftWidth = 130.dp,
                            imageWidth = 130.dp
                            )
                    }
                }
            }
        }
    }
}

