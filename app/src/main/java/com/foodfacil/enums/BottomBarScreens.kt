package com.foodfacil.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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

    data object Profile : BottomBarScreen(
        route = "COMBOS",
        title = "Combos",
        icon = Icons.Default.Person
    )

    data object Settings : BottomBarScreen(
        route = "PEDIDOS",
        title = "Pedidos",
        icon = Icons.Default.Settings
    )
}
