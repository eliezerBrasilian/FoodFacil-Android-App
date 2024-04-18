package com.foodfacil.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.updateAddress
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(){
    data class User(var userUid:String,
                    var name:String,
                    var token:String = "",
                    var profilePicture:String = ""
        )

    //private val _user = MutableLiveData(User("123456","Eliezer Assunção de Paulo paulo paulo paulo",emptyAddress))
    private val _user = MutableLiveData<User>()

    val user:LiveData<User> = _user

    val TAG = "USERVIEWMODEL"
    val print = Print(TAG)

    fun loadUserData(context: Context) = viewModelScope.launch{
        val sd = StoreUserData(context)

        _user.value?.userUid = sd.getUid.toString()
        _user.value?.token = sd.getToken.toString()
        _user.value?.profilePicture = sd.getPhoto.toString()

        print.log("userUid", _user.value?.userUid)
        print.log("token",  sd.getToken.toString())
    }

    fun addAddress(address: AddressDto,token: String,userId:String, onSuccess: () -> Unit) = viewModelScope.launch{
        val atualizado = updateAddress(address,token,userId)

        if(atualizado){
            onSuccess()
        }
    }

}