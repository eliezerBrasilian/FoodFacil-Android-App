package com.foodfacil.enums

 class NavigationScreens{
    companion object{
        val LOGIN = Screens.LOGIN.name
        val SIGN_UP = Screens.SIGN_UP.name
        val HOME =  Screens.HOME.name
        val PROFILE = Screens.PROFILE.name
        val SPLASH = Screens.SPLASH.name
        val GAME_SELECTED = Screens.GAME_SELECTED.name
        val ON_AUTH = "OnAuth"
        val ON_AUTH_LOGIN = "OnAuthLogin"
        val ON_AUTH_SIGN_UP = "OnAuthSignUp"
        val CHART = "Chart"
        val FINALIZAR_PEDIDO = "FinalizarPedido"
    }

      enum class Screens {
          LOGIN,
          SIGN_UP,
          HOME,
          PROFILE,
          SPLASH,
          GAME_SELECTED
     }
}
