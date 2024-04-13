package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.SalgadoResponseDto
import com.foodfacil.api.getAllSalgados
import com.foodfacil.dataClass.Salgado
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class SalgadosViewModel : ViewModel(){
    private val TAG = "SalgadosViewModel"
    private val print = Print(TAG)

    val salgados = mutableStateOf<List<SalgadoResponseDto>>(emptyList())


    fun getAllSalgados_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)

       val salgadosList = getAllSalgados(token)

        salgadosList.forEach{
            print.log("image", it.image)
        }
        salgados.value = salgadosList
    }

    fun salgadosInOfferList(): List<SalgadoResponseDto> {
        return salgados.value.filter { it.inOffer == true }
    }

  /*  fun salgadosNoCopoList():List<Salgado>{
        return salgados.filter { it.isSalgadoNoCopo == true }
    }*/

    fun findSalgado(id: String?): Salgado? {
        var founded =  salgados.value.find { it.id == id }

        if(founded != null){
            var salgado = Salgado(
                id = founded.id, title = founded.name, description = founded.description,
                price = founded.price, image = founded.image, inOffer = founded.inOffer,
                priceInOffer = founded.priceInOffer)
            return salgado;
        }
        return null;
    }

    fun getPedidos():List<Any>{
        return emptyList()
    }
}