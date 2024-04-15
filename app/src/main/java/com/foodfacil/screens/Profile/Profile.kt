package com.foodfacil.screens.Profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.azmithabet.circleimageviewcompose.CircleImage
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.R
import com.foodfacil.api.updateProfilePicture
import com.foodfacil.components.Line
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.viewModel.UserViewModel
import com.simpletext.SimpleText
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    paddingValues: PaddingValues,
    storeUserData: StoreUserData
) {
    val md = Modifier

    val userPhoto by storeUserData.getPhoto.collectAsState(initial = "")
    val userName by storeUserData.getName.collectAsState(initial = "")
    val userToken by storeUserData.getToken.collectAsState(initial = "")
    val userId by storeUserData.getUid.collectAsState(initial = "")

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val goBackToAuthScreen = {
        navController.navigate(NavigationScreens.ON_AUTH)
    }

    LaunchedEffect(Unit) {
        logAvailableGraphsAndRoutes(navController)
    }
    logAvailableGraphsAndRoutes(navController)

    val TAG = "Profile"
    val print = Print(TAG)

    val logout:()->Unit = {
        print.log("logout")
        coroutineScope.launch {
            StoreUserData(context).clearAllData()
            goBackToAuthScreen()
        }
    }

    Column(modifier = md
        .padding(paddingValues)
        .fillMaxSize()
        .background(Color.White)) {
        Top(md = md, userViewModel.user.value?.name, userPhoto,userName,userToken,userId)
        Line()

        ProfileMenuItem(md = md, title = "Dados da conta", destination = NavigationScreens.CUPONS, nav = navController)
        Line()

        ProfileMenuItem(md = md, title = "Cupons", destination = NavigationScreens.CUPONS, nav = navController)
        Line()

        ProfileMenuItem(md = md, title = "Endereço", destination = NavigationScreens.CUPONS, nav = navController)
        Line()
        ButtonLogout(logout)
    }
}

@Composable
private fun Top(
    md: Modifier,
    user: String?,
    userPhoto: String?,
    userName: String?,
    userToken: String?,
    userId: String?
){
    val TAG = "Profile"
    val print = Print(TAG)

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri->
        selectedImage = uri
        print.log("CAPA", uri.toString())
    }

    val escolheFotoDePerfil: ()->Unit = {
        print.log("escolher imagem")
        launcher.launch(arrayOf("image/*"))
    }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val salvar: ()->Unit = {
        coroutineScope.launch {
            updateProfilePicture(selectedImage.toString(),userToken,userId,context)
            selectedImage = null
        }
    }

    val descartar = {
        selectedImage = null
    }



    Box(modifier = md
        .height(180.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 20.dp)
       ,contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)) {

            if(userPhoto == null || userPhoto == ""){
                CircleImage(
                    painter = painterResource(id = R.drawable.combo_g),
                    contentDescription = null, size = 70.dp, clickable = true,
                    onClickImage = escolheFotoDePerfil
                )
            }else{
                AsyncImage(
                    model = if(selectedImage == null) userPhoto else selectedImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { escolheFotoDePerfil() }
                )
                if(selectedImage != null){

                    Buttons(descartar,salvar)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            SimpleText(userName.toString(), fontSize = 17)
        }
    }
}

@SuppressLint("RestrictedApi")
fun logAvailableGraphsAndRoutes(navController: NavHostController) {
    val TAG = "Profile"
    val print = Print(TAG)

    // Obtenha o NavigatorProvider
    val navigatorProvider = navController.navigatorProvider

    // Obtenha todos os gráficos de navegação disponíveis
    val graphs = mutableListOf<NavGraph>()


    navigatorProvider.navigators.forEach { navigator ->
        if (navigator is NavGraph) {
            graphs.add(navigator)
        }
    }

    // Iterar sobre cada gráfico de navegação
    graphs.forEach { graph ->
        print.log("Graph: ${graph.id}")

        // Iterar sobre cada rota no gráfico de navegação
        graph.forEach { destination ->
            print.log("  Route: ${destination.route}")
        }
    }
}

 @Composable
 private fun ProfileMenuItem(md: Modifier, title: String, destination: String, nav: NavHostController){
    Box(
        md
            .height(80.dp)
            .fillMaxWidth()
            .padding(start = 20.dp)
            .clickable { nav.navigate(destination) }
        , contentAlignment = Alignment.CenterStart) {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, md.size(25.dp))
            SimpleText(title, fontSize = 17)
        }
    }
}

@Composable
private fun Buttons(descartar:()->Unit = {}, salvar:()-> Unit = {}){
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = descartar, colors = ButtonDefaults.buttonColors(Color.LightGray)) {
            SimpleText("Descartar", fontSize = 16, color = Color.White)
        }
        Button(onClick =  salvar ) {
            SimpleText("Salvar", fontSize = 16, color = Color.White)
        }
    }
}

@Composable
fun ButtonLogout(onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp), contentAlignment = Alignment.Center){
       FilledTonalButton(onClick = { onClick() }) {
           SimpleText("Encerrar sessão", color = Color.DarkGray, fontSize = 16)
       }
    }
}