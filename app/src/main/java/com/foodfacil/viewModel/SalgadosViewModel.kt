package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getAllAdicionais
import com.foodfacil.api.getAllSalgados
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.Salgado
import com.foodfacil.dataclass.SalgadoDto
import com.foodfacil.enums.Categoria
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class SalgadosViewModel : ViewModel(){
    private val TAG = "SalgadosViewModel"
    private val print = Print(TAG)

    val salgados = mutableStateOf<List<SalgadoDto>>(emptyList())
    val adicionais = mutableStateOf<List<AdicionalDto>>(emptyList())

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun getAllSalgados_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)
        _loading.value=true

       val salgadosList = getAllSalgados(token)

        val salgadosDtoList = mutableListOf<SalgadoDto>()
        salgadosList.forEach{
            print.log("image", it.image)

            salgadosDtoList.add(
                SalgadoDto(
                    id = it.id,
                    image = it.image,
                    imageQuadrada = it.imageQuadrada,
                    imageRetangular = it.imageRetangular,
                    categoria = it.categoria,
                    description = it.description, name = it.name, inOffer = it.inOffer,
                    priceInOffer = it.priceInOffer, price = it.price,
                    disponibilidade = it.disponibilidade,
                    acompanhamentos = it.acompanhamentos
                )
            )
        }

        salgados.value = salgadosDtoList
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

    fun salgadosInOfferList(): List<SalgadoDto> {
        return salgados.value.filter { it.inOffer == true }
    }

    fun combosList():List<SalgadoDto>{
        return salgados.value.filter { it.categoria == Categoria.COMBO }
    }
    fun batataRosti(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.BATATAS }
    }

    fun findSalgado(id: String?): Salgado? {
        val founded =  salgados.value.find { it.id == id }

        if(founded != null){
            val salgado = Salgado(
                id = founded.id,
                title = founded.name,
                description = founded.description,
                price = founded.price,
                priceInOffer = founded.priceInOffer,
                image = founded.image,
                imageQuadrada = founded.imageQuadrada,
                imageRetangular = founded.imageRetangular,
                inOffer = founded.inOffer,
                acompanhamentos = founded.acompanhamentos)
            return salgado;
        }
        return null;
    }

}