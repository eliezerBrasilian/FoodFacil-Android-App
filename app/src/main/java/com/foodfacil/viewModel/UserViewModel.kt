package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel(){
    data class User(val userUid:String, val name:String)
    val user = mutableStateOf<User>(User("123456","Eliezer Assunção de Paulo paulo paulo paulo"))

}