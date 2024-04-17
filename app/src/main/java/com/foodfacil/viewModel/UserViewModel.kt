package com.foodfacil.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.dataclass.AddressResponseDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(){
    private val addressResponseDto = AddressResponseDto(rua = "Bom Jesus", bairro = "Praça de esportes", numero = "50")
    private val emptyAddress = null
    data class User(var userUid:String,
                    var name:String,
                    var token:String = "",
                    var addressResponseDto: AddressResponseDto? = null,
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

    fun addAddress(addressResponseDto: AddressResponseDto){
        _user.value?.addressResponseDto = addressResponseDto

        print.log(addressResponseDto)
    }

}