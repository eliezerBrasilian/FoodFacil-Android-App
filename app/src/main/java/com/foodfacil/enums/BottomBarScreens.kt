package com.foodfacil.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.foodfacil.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val imageIcon:Int,
    val imageIconSelected:Int
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        icon = Icons.Default.Home,
        imageIcon = R.drawable.home_icone_padrao,
        imageIconSelected = R.drawable.home_icone_selecionado
    )

    data object Cardapio : BottomBarScreen(
        route = "CARDAPIO",
        title = "Cardapio",
        icon = Icons.Default.CheckCircle,
        imageIcon = R.drawable.cardapio_icone_padrao,
        imageIconSelected = R.drawable.cardapio_icone_selecionado
    )

    data object Pedidos : BottomBarScreen(
        route = "PEDIDOS",
        title = "Pedidos",
        icon = Icons.Default.Menu,
        imageIcon = R.drawable.pedido_icone_padrao,
        imageIconSelected = R.drawable.pedido_icone_selecionado
    )

    data object Perfil : BottomBarScreen(
        route = "PERFIL",
        title = "Perfil",
        icon = Icons.Default.Person,
        imageIcon = R.drawable.perfil_icone_padrao,
        imageIconSelected = R.drawable.perfil_icone_selecionado
    )
}
