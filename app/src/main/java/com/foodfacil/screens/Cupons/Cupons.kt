package com.foodfacil.screens.Cupons


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.components.BackButtonWithTitle
import com.foodfacil.components.Circle
import com.foodfacil.components.CupomSkeletonLoadingList
import com.foodfacil.dataclass.CupomDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.utils.AppDateTime
import com.foodfacil.viewModel.CuponsViewModel
import com.gamestate.utils.Toast
import com.simpletext.SimpleText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cupons(
    nav: NavHostController,
    paddingValues: PaddingValues,
    cuponsViewModel: CuponsViewModel,
    storeUserData: StoreUserData
) {

    val md = Modifier;

    val cupons by cuponsViewModel.cuponsList.collectAsState(initial = emptyList())
    val loading by cuponsViewModel.loading.observeAsState(initial = true)
    val userToken by storeUserData.getToken.collectAsState(initial = "")
    val userId by storeUserData.getUid.collectAsState(initial = "")

    val cuponsResgatadosIds = remember {
        mutableStateListOf<String?>(null)
    }

    val tag = "CuponsViewModel"
    val print = Print(tag)

    LaunchedEffect(key1 = true) {
        print.log("aqui")
        cuponsViewModel.loadCupons(userToken.toString(), userId.toString())
    }

    val context = LocalContext.current

    val resgatarCupom: (cupom: CupomDto) -> Unit = {
        cuponsViewModel.addCupomToAccount(it, userToken.toString(), userId.toString()) {
            cuponsResgatadosIds.add(it.id)
            Toast(context).showToast("cupom resgatado")
        }
    }

    Scaffold(
        topBar = { BackButtonWithTitle(navController = nav, title = "Cupons") },
        modifier = md.padding(paddingValues)
    ) { pv ->
        Surface(modifier = md.padding(pv)) {
            if (loading) {
                CupomSkeletonLoadingList(modifier = Modifier.padding(15.dp))
            } else {
                MainContent(cupons = cupons, resgatarCupom = resgatarCupom, cuponsResgatadosIds = cuponsResgatadosIds)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    cupons: List<CupomDto>,
    resgatarCupom: (cupom: CupomDto) -> Unit,
    cuponsResgatadosIds: SnapshotStateList<String?>
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
    ) {
        SimpleText("Cupons de 15%")
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(cupons) { cupom ->
                val resgatado = if (cupom.resgatado || cuponsResgatadosIds.contains(cupom.id)) true else false
                CupomItem(
                    cupom, resgatarCupom,
                    resgatado = resgatado
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CupomItem(
    cupom: CupomDto,
    resgatarCupom: (cupom: CupomDto) -> Unit,
    resgatado: Boolean?
) {
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White, elevation = 10.dp) {
        Box(modifier = Modifier.padding(10.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CupomTop(cupom.porcentoDeDesconto)
                CupomBottom(cupom, resgatarCupom, resgatado)
            }
        }
    }
}

@Composable
private fun CupomTop(porcentoDeDesconto: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CupomTopLeftItem(porcentoDeDesconto)
        Column {
            SimpleText("Lanches verificados", color = Color.DarkGray, fontSize = 15)
            SimpleText("Cupom de ${porcentoDeDesconto}% de Desconto", fontSize = 17)
            SimpleText("Válido em toda cidade de Brás Pires", color = Color.DarkGray, fontSize = 14)
        }
    }
}

@Composable
private fun CupomTopLeftItem(porcentoDeDesconto: Int) {
    Circle(color = Color.Green, size = 90.dp) {
        Box(modifier = Modifier) {
            SimpleText("$porcentoDeDesconto%")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CupomBottom(
    cupom: CupomDto,
    resgatarCupom: (cupom: CupomDto) -> Unit,
    resgatado: Boolean?
) {

    val expirationDate = cupom.expirationDate
    val description = cupom.description
    val TAG = "CuponsScreen"
    val print = Print(TAG)

    val detalhesExpanded = remember {
        mutableStateOf(false)
    }

    val formattedDate = AppDateTime().getBrazilianDate(expirationDate)

    print.log("expirationDate", expirationDate)
    print.log("expirationDateFormatado", formattedDate)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                SimpleText("Válido até $formattedDate", color = Color.DarkGray, fontSize = 12)
                Box(modifier = Modifier.clickable {
                    detalhesExpanded.value = !detalhesExpanded.value
                }) {
                    SimpleText("Detalhes", color = Color(0xFF2990BE), fontSize = 12)
                }
            }
            if (resgatado == false) {
                Button(onClick = { resgatarCupom(cupom) }) {
                    SimpleText("Resgatar", color = Color.White, fontSize = 12)
                }
            } else {
                OutlinedButton(
                    onClick = { resgatarCupom(cupom) },
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background)
                ) {
                    SimpleText("Já resgatado", fontSize = 12)
                }
            }
        }

        if (detalhesExpanded.value)
            Box {
                SimpleText(description, fontSize = 15)
            }
    }
}

