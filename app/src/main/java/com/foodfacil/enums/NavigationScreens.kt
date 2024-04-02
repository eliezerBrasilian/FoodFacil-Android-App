package com.gamestate.enums

 class NavigationScreens{
    companion object{
        val LOGIN = Screens.LOGIN.name
        val SIGN_UP = Screens.SIGN_UP.name
        val HOME =  Screens.HOME.name
        val PROFILE = Screens.PROFILE.name
        val SPLASH = Screens.SPLASH.name
        val GAME_SELECTED = Screens.GAME_SELECTED.name
        val ON_AUTH = "OnAuth"
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
