package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.foodfacil.dataClass.Address

class UserViewModel : ViewModel(){
    private val address = Address(rua = "Bom Jesus", bairro = "Praça de esportes", numero = 50)
    data class User(val userUid:String, val name:String, val address: Address? = null)
    val user = mutableStateOf<User>(User("123456","Eliezer Assunção de Paulo paulo paulo paulo",address))

}