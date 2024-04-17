package com.foodfacil.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getAllCupons
import com.foodfacil.api.getAllCuponsOfUser
import com.foodfacil.api.resgatarCupom
import com.foodfacil.dataclass.CupomDto
import com.foodfacil.dataclass.SimpleCupomDto
import com.foodfacil.dataclass.UserCupomDto
import com.foodfacil.services.Print
import com.gamestate.utils.Toast
import kotlinx.coroutines.launch

class CuponsViewModel:ViewModel() {

    val print = Print("CuponsViewModel")

    private val _cupons = MutableLiveData<List<CupomDto>>(emptyList())
    val cuponsList: LiveData<List<CupomDto>> = _cupons

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun loadCupons(userToken:String, userId: String) = viewModelScope.launch{
        val cupons = getAllCupons(userToken)
        val cuponsOfUser = getAllCuponsOfUser(token = userToken.toString(), userId = userId)

        for(cupomUsuario in cuponsOfUser.cupoms){
            val cupomResgatado = cupons.cupoms.find { it.id == cupomUsuario.id }
            if(cupomResgatado!=null){
                val index = cupons.cupoms.indexOf(cupomResgatado)
                cupons.cupoms[index].resgatado = true
            }
            print.log("cupom resgatado", cupomResgatado)
        }
        _cupons.value = cupons.cupoms
        _loading.value = false
    }

    fun addCupomToAccount(cupomDto: CupomDto, context:Context,
                          token:String, userId:String, onSuccess:()->Unit) = viewModelScope.launch{

        val resgatouCupom = resgatarCupom(token,UserCupomDto(userId,
            SimpleCupomDto(id = cupomDto.id, resgatado = true,used = false)))

        print.log("resgatouCpom",resgatouCupom)

        if(resgatouCupom){
            val cuponsAux = (_cupons.value?: emptyList()).toMutableList()

            print.log("cupons antes",_cupons.value)
            val cuponEncontrado = cuponsAux.find{
                it.id == cupomDto.id}

            val index = cuponsAux.indexOf(cuponEncontrado)
            cuponsAux[index].used = true

            _cupons.value = cuponsAux

            print.log("cupons depois",_cupons.value)

            Toast(context).showToast("cupom adicionado")
            onSuccess()
        }
    }
}