package com.foodfacil.screens.Profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.azmithabet.circleimageviewcompose.CircleImage
import com.foodfacil.viewModel.AuthViewModel
import com.foodfacil.R
import com.foodfacil.components.Line
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.viewModel.UserViewModel
import com.simpletext.SimpleText

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

    Column(modifier = md
        .padding(paddingValues)
        .fillMaxSize()
        .background(Color.White)) {
        Top(md = md, userViewModel.user.value?.name, userPhoto,userName)
        Line()

        ProfileMenuItem(md = md, title = "Dados da conta")
        Line()

        ProfileMenuItem(md = md, title = "Cupons")
        Line()

        ProfileMenuItem(md = md, title = "Endereço")
        Line()
    }
}

@Composable
private fun Top(md: Modifier, user: String?, userPhoto: String?, userName: String?){
    Box(modifier = md
        .height(180.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
       ,contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)) {

            if(userPhoto == null || userPhoto == ""){
                CircleImage(
                    painter = painterResource(id = R.drawable.combo_g),
                    contentDescription = null, size = 70.dp
                )
            }else{
                AsyncImage(
                    model = userPhoto,
                    contentDescription = null, modifier = Modifier.size(70.dp).clip(CircleShape)
                )
            }

            SimpleText(userName.toString(), fontSize = 17)
        }
    }
}

 @Composable
 private fun ProfileMenuItem(md:Modifier, title:String){
    Box(
        md
            .height(80.dp)
            .fillMaxWidth()
            .padding(start = 20.dp), contentAlignment = Alignment.CenterStart) {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, md.size(25.dp))
            SimpleText(title, fontSize = 17)
        }
    }
}


