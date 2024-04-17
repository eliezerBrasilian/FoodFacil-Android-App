package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getAllAdicionais
import com.foodfacil.dataclass.SalgadoResponseDto
import com.foodfacil.api.getAllSalgados
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.Salgado
import com.foodfacil.enums.Categoria
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class SalgadosViewModel : ViewModel(){
    private val TAG = "SalgadosViewModel"
    private val print = Print(TAG)

    val salgados = mutableStateOf<List<SalgadoResponseDto>>(emptyList())
    val adicionais = mutableStateOf<List<AdicionalDto>>(emptyList())

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun getAllSalgados_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)
        _loading.value=true

       val salgadosList = getAllSalgados(token)

        salgadosList.forEach{
            print.log("image", it.image)
        }
        salgados.value = salgadosList
        _loading.value=false
    }
    fun getAllAdicionais_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)

        val list = getAllAdicionais(token)

        list.forEach{
            print.log("name", it.titulo)
        }
        adicionais.value = list
    }

    fun salgadosInOfferList(): List<SalgadoResponseDto> {
        return salgados.value.filter { it.inOffer == true }
    }
    fun batataRosti(): List<SalgadoResponseDto> {
        return salgados.value.filter { it.categoria == Categoria.BATATAS }
    }

    fun findSalgado(id: String?): Salgado? {
        var founded =  salgados.value.find { it.id == id }

        if(founded != null){
            var salgado = Salgado(
                id = founded.id, title = founded.name, description = founded.description,
                price = founded.price, image = founded.image, inOffer = founded.inOffer,
                priceInOffer = founded.priceInOffer, acompanhamentos = founded.acompanhamentos)
            return salgado;
        }
        return null;
    }

}