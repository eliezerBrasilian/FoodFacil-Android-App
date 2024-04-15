package com.foodfacil.screens.Cupons


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.Circle
import com.foodfacil.dataclass.CupomDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.utils.AppDateTime
import com.foodfacil.viewModel.CuponsViewModel
import com.simpletext.SimpleText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cupons(nav:NavHostController,
           paddingValues: PaddingValues,
           cuponsViewModel: CuponsViewModel,
           storeUserData: StoreUserData
           ){

    val md = Modifier;

    val cupons by cuponsViewModel.cuponsList.observeAsState(initial = emptyList())
    val userToken by storeUserData.getToken.collectAsState(initial = "")
    val userId by storeUserData.getUid.collectAsState(initial = "")

    val cuponsResgatadosIds = remember {
        mutableStateListOf<String?>(null)
    }

    val tag = "CuponsViewModel"
    val print = Print(tag)

    LaunchedEffect(key1 = true){
        print.log("aqui")
        cuponsViewModel.loadCupons(userToken.toString(), userId.toString())
    }

    val context = LocalContext.current

    val resgatarCupom: (cupom: CupomDto)->Unit =  {
        cuponsViewModel.addCupomToAccount(it,context, userToken.toString(), userId.toString()){
            cuponsResgatadosIds.add(it.id)
        }
    }

    Scaffold(topBar = { BackButtonWithTitle(navController = nav, title = "Cupons") },
        modifier = md.padding(paddingValues)) {pv->
        Surface(modifier = md.padding(pv)) {
            Column(modifier = Modifier.padding(15.dp)) {
                SimpleText("Cupons de 15%")
                Spacer(modifier = Modifier.height(15.dp))
                cupons.forEach{cupom->

                    val resgtado = if(cuponsResgatadosIds.contains(cupom.id) || cupom.resgatado) true else false
                    print.log("resgtado",resgtado)

                    CupomItem(cupom,resgatarCupom,
                        resgatado = resgtado)
                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CupomItem(cupom: CupomDto, resgatarCupom: (cupom: CupomDto) -> Unit, resgatado: Boolean?) {
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White, elevation = 10.dp){
        Box(modifier = Modifier.padding(10.dp)){
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CupomTop(cupom.porcentoDeDesconto)
                CupomBottom(cupom,resgatarCupom,resgatado)
            }
        }
    }
}

@Composable
fun CupomTop(porcentoDeDesconto: Int) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()) {
        CupomTopLeftItem()
        Column {
            SimpleText("Lanches verificados",color = Color.DarkGray, fontSize = 15)
            SimpleText("Cupom de ${porcentoDeDesconto}% de Desconto", fontSize = 17)
            SimpleText("Válido em toda cidade de Brás Pires",color = Color.DarkGray, fontSize = 14)
        }
    }
}

@Composable
fun CupomTopLeftItem(){
    Circle(color = Color.Green,  size = 90.dp) {
        Box(modifier = Modifier,){
            SimpleText("15%")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CupomBottom(cupom: CupomDto, resgatarCupom: (cupom: CupomDto) -> Unit, resgatado: Boolean?) {

    val expirationDate = cupom.expirationDate
    val description = cupom.description
    val TAG = "CuponsScreen"
    val print = Print(TAG)

    val detalhesExpaded = remember{
        mutableStateOf(false)
    }

    val formattedDate = AppDateTime().getBrazilianDate(expirationDate)

    print.log("expirationDate", expirationDate)
    print.log("expirationDateFormatado", formattedDate)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)){
                SimpleText("Válido até $formattedDate",color = Color.DarkGray, fontSize = 12)
                Box(modifier = Modifier.clickable { detalhesExpaded.value = !detalhesExpaded.value  }){
                    SimpleText("Detalhes",color = Color(0xFF2990BE), fontSize = 12)
                }
            }
            if(resgatado == false){
                Button(onClick = { resgatarCupom(cupom) }) {
                    SimpleText("Resgatar",color = Color.White, fontSize = 12)
                }
            }

            else{
                OutlinedButton(onClick = { resgatarCupom(cupom) },
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background)) {
                    SimpleText("Já resgatado", fontSize = 12)
                }
            }
        }

        if(detalhesExpaded.value)
        Box {
            SimpleText(description, fontSize = 15)
        }
    }

}

