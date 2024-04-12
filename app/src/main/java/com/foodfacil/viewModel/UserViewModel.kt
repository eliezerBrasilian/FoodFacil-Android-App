package com.foodfacil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodfacil.dataClass.Address
import com.foodfacil.services.Print

class UserViewModel : ViewModel(){
    private val address = Address(rua = "Bom Jesus", bairro = "Praça de esportes", numero = "50")
    private val emptyAddress = null
    data class User(val userUid:String, val name:String, var address: Address? = null)
    private val _user = MutableLiveData(User("123456","Eliezer Assunção de Paulo paulo paulo paulo",emptyAddress))

    val user:LiveData<User> = _user

    val TAG = "USERVIEWMODEL"
    val print = Print(TAG)

    fun addAddress(address: Address){
        _user.value?.address = address

        print.log(address)
    }
}