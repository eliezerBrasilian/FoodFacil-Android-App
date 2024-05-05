package com.foodfacil.screens.DadosDaConta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.CustomTopBar
import com.foodfacil.components.DadosDaContaItem
import com.foodfacil.components.VazioComponentText
import com.foodfacil.datastore.StoreUserData

@Composable

fun DadosDaConta(
    paddingValues: PaddingValues,
    storeUserData: StoreUserData,
    navController: NavHostController
) {
    val nome = storeUserData.getName.collectAsState(initial = "")
    val email = storeUserData.getEmail.collectAsState(initial = "")

    Scaffold(modifier = Modifier.padding(paddingValues), topBar = {  CustomTopBar(title = "Dados da conta", navController) }) { pv->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(pv)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                if(nome.value.isNullOrEmpty()){
                    VazioComponentText(md = Modifier, text = "Você ainda não cadastrou nenhum endereço")
                }else{
                        Spacer(modifier = Modifier.height(40.dp))
                        DadosDaContaItem(nome.value, "Nome")
                        DadosDaContaItem(email.value, "Email")
                }

            }
        }
    }
}