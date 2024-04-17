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
import com.foodfacil.enums.CupomCategory
import com.foodfacil.services.Print
import com.gamestate.utils.Toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual

class CuponsViewModel:ViewModel() {

    val print = Print("CuponsViewModel")

    private val _cupons = MutableStateFlow<List<CupomDto>>(emptyList())
    val cuponsList: StateFlow<List<CupomDto>> = _cupons

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun loadCupons(userToken:String, userId: String) = viewModelScope.launch{
        val cuponsResponseDto = getAllCupons(userToken)

        val cuponsDto = mutableListOf<CupomDto>()

        cuponsResponseDto.cupoms.forEach{
            val cupomDtoItem = CupomDto(id = it.id, code = it.code, porcentoDeDesconto = it.porcentoDeDesconto,
                createdAt = it.createdAt, expirationDate = it.expirationDate, expired = it.expired, used = false,
                description = it.description, cupomCategory = it.cupomCategory, resgatado = false
                )
            cuponsDto.add(cupomDtoItem)
        }

        print.log("cupons gerais ${cuponsResponseDto.cupoms}")
        print.log("cupons convertidos ${cuponsDto}")

        val cuponsOfUser = getAllCuponsOfUser(token = userToken, userId = userId)

        print.log("cupons do usuario ${cuponsOfUser.cupoms}")

        for(cupomUsuario in cuponsOfUser.cupoms){
            val cupomResgatado = cuponsDto.find { it.id == cupomUsuario.id }
            if(cupomResgatado!=null){
                val index = cuponsDto.indexOf(cupomResgatado)
                cuponsDto[index].resgatado = true
            }
            print.log("cupom ${cupomUsuario.id}")
            print.log("cupom resgatado", cupomResgatado)
        }

        _cupons.value = cuponsDto

        print.log("quantidade de cupons: ${_cupons.value!!.size}")
        _loading.value = false
    }

    fun addCupomToAccount(cupomDto: CupomDto,
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

            onSuccess()
        }
    }
}