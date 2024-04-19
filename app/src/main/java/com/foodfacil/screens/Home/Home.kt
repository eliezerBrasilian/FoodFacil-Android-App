package com.foodfacil.screens.Home

import NavigationBarColor
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.carrousel.Carrousel
import com.foodfacil.R
import com.foodfacil.components.HomeHeader
import com.foodfacil.components.RowVerCarrinho
import com.foodfacil.components.SalgadoItem
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.ChartViewModel
import com.foodfacil.viewModel.SalgadosViewModel
import com.foodfacil.viewModel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("InlinedApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavHostController,
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
    val loading = salgadosViewModel.loading.observeAsState(initial = true)

    // Android 13 Api 33 - runtime notification permission has been added
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(key1 = Unit) {
        if (notificationPermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            Firebase.messaging.subscribeToTopic("Tutorial")
        } else {
            //  showNotificationDialog.value = true
            notificationPermissionState.launchPermissionRequest()
        }

        salgadosViewModel.getAllSalgados_(userToken.toString())
        salgadosViewModel.getAllAdicionais_(userToken.toString())
    }

    NavigationBarColor(color = Color.White)

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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {

                Spacer(md.height(20.dp))
                Text(
                    "Tá no app",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(md.height(20.dp))
                Carrousel(
                    imagesResources = listOf(
                        R.drawable.banner1,
                        R.drawable.salgados_img_on_auth,
                        R.drawable.salgados_img_on_auth
                    ),
                    imageActiveColor = MainYellow,
                    imageInactiveColor = Color(0xffE0E0E0),
                    isCircle = true,
                    dotHeight = 8.dp,
                    dotWidth = 8.dp
                )
                Spacer(md.height(30.dp))

                Text(
                    "Promoções Imperdíveis",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(md.height(10.dp))
                if (loading.value) {
                    Box(modifier = md.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            color = MainYellow,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = md.width(50.dp)
                        )
                    }
                } else {
                    if(salgadosViewModel.salgadosInOfferList().isEmpty()){
                        Column(modifier = md.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                            Text(
                                "Não temos promoções no momento, mas fique ligado(a) :) no app",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        salgadosViewModel.salgadosInOfferList().forEach { salgado ->
                            SalgadoItem(md = md, salgado = salgado, navController = navController)
                        }
                    }

                }
            }
        }
    }
}

