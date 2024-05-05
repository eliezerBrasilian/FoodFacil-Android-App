package com.foodfacil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getAllAcompanhamentos
import com.foodfacil.api.getAllSalgados
import com.foodfacil.dataclass.Adicional
import com.foodfacil.dataclass.Salgado
import com.foodfacil.enums.Categoria
import com.foodfacil.services.Print
import kotlinx.coroutines.launch

class SalgadosViewModel : ViewModel(){
    private val print = Print()

    val salgados = mutableStateOf<List<Salgado>>(emptyList())
    val adicionais = mutableStateOf<List<Adicional>>(emptyList())

    private val _loading = MutableLiveData<Boolean>(true)
    val loading:LiveData<Boolean> = _loading

    fun getAllSalgados_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)
        _loading.value=true

       val salgadosResponseList = getAllSalgados(token)
        val salgadosList = mutableListOf<Salgado>()

        salgadosResponseList.forEach{
            print.log("image", it.imagem)

            salgadosList.add(
                Salgado(
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
                    disponibilidade = it.disponibilidade,
                    sabores = it.sabores
                )
            )
        }

        salgados.value = salgadosList
        _loading.value=false
    }

    fun getAllAdicionais_(token:String) = viewModelScope.launch{
        print.log("token recebido",token)

        val list = getAllAcompanhamentos(token)

        val adicionaisListMapped = mutableListOf<Adicional>()

        list.forEach{
            val adicionalItem = Adicional(id = it.id, imagem = it.imagem, titulo = it.nome,
                descricao = it.descricao, preco = it.preco, disponibilidade = it.disponibilidade)
            adicionaisListMapped.add(adicionalItem)
        }
        adicionais.value = adicionaisListMapped
    }

    fun salgadosNoCopoList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.SALGADONOCOPO }
    }
    fun risoleList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.RISOLE }
    }
    fun coxinhaList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.COXINHA }
    }
    fun pastelList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.PASTEL }
    }
    fun hotDogList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.HOTDOG }
    }
    fun combosList():List<Salgado>{
        return salgados.value.filter { it.categoria == Categoria.COMBO }
    }
    fun congeladoList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.CONGELADOS }
    }

    fun batataRostiList(): List<Salgado> {
        return salgados.value.filter { it.categoria == Categoria.BATATAS }
    }

    fun salgadosInOfferList(): List<Salgado> {
        return salgados.value.filter { it.emOferta == true }
    }

    fun findSalgado(id: String?): Salgado? {
        val founded =  salgados.value.find { it.id == id }

        if(founded != null){
            val salgado = Salgado(
                id = founded.id,
                nome = founded.nome,
                categoria = founded.categoria,
                descricao = founded.descricao,
                preco = founded.preco,
                imagem = founded.imagem,
                imagemQuadrada = founded.imagemQuadrada,
                imagemRetangular = founded.imagemRetangular,
                emOferta = founded.emOferta,
                precoEmOferta = founded.precoEmOferta,
                disponibilidade = founded.disponibilidade,
                sabores = founded.sabores)
            return salgado;
        }
        return null;
    }

}