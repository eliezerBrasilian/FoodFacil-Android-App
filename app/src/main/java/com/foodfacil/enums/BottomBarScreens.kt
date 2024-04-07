package com.foodfacil.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        icon = Icons.Default.Home
    )

    data object Combos : BottomBarScreen(
        route = "CARDAPIO",
        title = "Cardapio",
        icon = Icons.Default.CheckCircle
    )

    data object Pedidos : BottomBarScreen(
        route = "PEDIDOS",
        title = "Pedidos",
        icon = Icons.Default.Menu
    )

    data object Perfil : BottomBarScreen(
        route = "PERFIL",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}
