package com.foodfacil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.updateAddress
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.services.Print
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(){
    data class User(var userUid:String,
                    var name:String,
                    var token:String = "",
                    var profilePicture:String = ""
        )


    private val _user = MutableLiveData<User>()
    val user:LiveData<User> = _user

    private val _pixSelecionado:MutableStateFlow<Boolean>  = MutableStateFlow(true)
    val pixSelecionado:StateFlow<Boolean> = _pixSelecionado

    private val _valorApagarEmDinheiro:MutableStateFlow<Float>  = MutableStateFlow(0f)
    val valorApagarEmDinheiro:StateFlow<Float> = _valorApagarEmDinheiro

    private val _precisaDeTroco:MutableStateFlow<Boolean>  = MutableStateFlow(false)
    val precisaDeTroco:StateFlow<Boolean> = _precisaDeTroco

    val print = Print()

    val handleEscolhePix: (ehPix:Boolean) -> Unit = { ehPix->
        _pixSelecionado.value = ehPix
    }

    val handlePrecisaDeTroco: (precisa:Boolean) -> Unit = {precisa->
        _precisaDeTroco.value = precisa
    }

    val handlePagaNoDinheiro: (valor:Float) -> Unit = {valor->
        _valorApagarEmDinheiro.value = valor
    }


    fun addAddress(address: AddressDto,token: String,userId:String, onSuccess: () -> Unit) = viewModelScope.launch{
        val atualizado = updateAddress(address,token,userId)

        if(atualizado){
            onSuccess()
        }
    }

}