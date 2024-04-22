package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getAllAdicionais
import com.foodfacil.api.getAllSalgados
import com.foodfacil.dataclass.Adicional
import com.foodfacil.dataclass.Salgado
import com.foodfacil.dataclass.SalgadoDto
import com.foodfacil.enums.Categoria
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class SalgadosViewModel : ViewModel(){
    private val print = Print()

    val salgados = mutableStateOf<List<SalgadoDto>>(emptyList())
    val adicionais = mutableStateOf<List<Adicional>>(emptyList())

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun getAllSalgados_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)
        _loading.value=true

       val salgadosList = getAllSalgados(token)

        val salgadosDtoList = mutableListOf<SalgadoDto>()
        salgadosList.forEach{
            print.log("image", it.imagem)

            salgadosDtoList.add(
                SalgadoDto(
                    id = it.id,
                    nome = it.nome,
                    categoria = it.categoria,
                    descricao = it.descricao,
                    preco = it.preco,
                    imagem = it.imagem,
                    imagemRetangular = it.imagemRetangular,
                    imagemQuadrada = it.imagemQuadrada,
                    emOferta = it.emOferta,
                    precoEmOferta = it.precoEmOferta,
                    createdAt = it.createdAt,
                    observacao = it.observacao,
                    disponibilidade = it.disponibilidade,
                    ingredientes = it.ingredientes
                )
            )
        }

        salgados.value = salgadosDtoList
        _loading.value=false
    }

    fun getAllAdicionais_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)

        val list = getAllAdicionais(token)

        val adicionaisListMapped = mutableListOf<Adicional>()

        list.forEach{
            val adicionalItem = Adicional(id = it.id, imagem = it.imagem, titulo = it.titulo,
                descricao = it.descricao, preco = it.preco, disponibilidade = it.disponibilidade)
            adicionaisListMapped.add(adicionalItem)
        }
        adicionais.value = adicionaisListMapped
    }

    fun salgadosNoCopoList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.SALGADONOCOPO }
    }
    fun risoleList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.RISOLE }
    }
    fun coxinhaList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.COXINHA }
    }
    fun pastelList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.PASTEL }
    }
    fun hotDogList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.HOTDOG }
    }
    fun combosList():List<SalgadoDto>{
        return salgados.value.filter { it.categoria == Categoria.COMBO }
    }
    fun congeladoList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.CONGELADOS }
    }

    fun batataRostiList(): List<SalgadoDto> {
        return salgados.value.filter { it.categoria == Categoria.BATATAS }
    }

    fun salgadosInOfferList(): List<SalgadoDto> {
        return salgados.value.filter { it.emOferta == true }
    }

    fun findSalgado(id: String?): Salgado? {
        val founded =  salgados.value.find { it.id == id }

        if(founded != null){
            val salgado = Salgado(
                id = founded.id,
                title = founded.nome,
                description = founded.descricao,
                price = founded.preco,
                priceInOffer = founded.precoEmOferta,
                image = founded.imagem,
                imageQuadrada = founded.imagemQuadrada,
                imageRetangular = founded.imagemRetangular,
                inOffer = founded.emOferta,
                acompanhamentos = founded.ingredientes)
            return salgado;
        }
        return null;
    }

}