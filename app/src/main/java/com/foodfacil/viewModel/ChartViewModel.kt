package com.foodfacil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.Salgado
import com.foodfacil.services.Print
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChartViewModel : ViewModel(){
    private val _chartList = MutableLiveData<List<Salgado>>(emptyList())
    val chartList:LiveData<List<Salgado>> = _chartList

    private val _adicionais = MutableLiveData<List<AdicionalDto>>(emptyList())
    val adicionais:LiveData<List<AdicionalDto>> = _adicionais

    private val _priceTotal = MutableStateFlow<Float>(0f)
    val priceTotal:StateFlow<Float> = _priceTotal

    val print = Print("CHARTVIEWMODEL")

    fun increment(salgadoId: String, salg: Salgado){
        if(_chartList.value?.contains(salg) == true){
            val atualCharList = (_chartList.value?: emptyList()).toMutableList()
            val index = atualCharList.indexOf(salg)
            print.log("index: ", index)

            val atualAmountSalgado = atualCharList[index].amount

            val atualSalgadoAlterado =  atualCharList[index].copy(amount =  atualAmountSalgado + 1)
            atualCharList[index] = atualSalgadoAlterado

            _chartList.value = atualCharList //lista atual + o salgado
            //incrementar 1 no carrinho

            val salgadoPrice = if(salg.inOffer)salg.priceInOffer else salg.price
            print.log("salgadoPreço",salgadoPrice)
            _priceTotal.value += salgadoPrice
        }
    }

    fun decrement(salgadoId: String, salg: Salgado){
        if(_chartList.value?.contains(salg) == true){
            val atualCharList = (_chartList.value?: emptyList()).toMutableList()
            val index = atualCharList.indexOf(salg)
            print.log("index: ", index)

            val salgadoPrice = if(salg.inOffer)salg.priceInOffer else salg.price
            _priceTotal.value -= salgadoPrice

            val atualAmountSalgado = atualCharList[index].amount
            val newAmount = atualAmountSalgado - 1

            if(newAmount == 0){
                //remove
                print.log("vazio")
                atualCharList.removeAt(index)
                _chartList.value = atualCharList
                return
            }

            val atualSalgadoAlterado =  atualCharList[index].copy(amount =  newAmount)
            atualCharList[index] = atualSalgadoAlterado

            _chartList.value = atualCharList //lista atual + o salgado
        }
    }

    fun addSalgadoToChart(salgado: Salgado?){
        if(salgado == null){
            print.log("não pode adicionar nulo")
            return
        }
        else if(_chartList.value?.contains(salgado) == true){
                val atualCharList = (_chartList.value?: emptyList()).toMutableList()
                val index = atualCharList.indexOf(salgado)
                print.log("index: ", index)

               val atualAmountSalgado = atualCharList[index].amount

               val atualSalgadoAlterado =  atualCharList[index].copy(amount =  atualAmountSalgado + 1)
               atualCharList[index] = atualSalgadoAlterado

                _chartList.value = atualCharList //lista atual + o salgado
                //incrementar 1 no carrinho


            val salgadoPrice = if(salgado.inOffer)salgado.priceInOffer else salgado.price
            print.log("salgadoPreço",salgadoPrice)
            _priceTotal.value += salgadoPrice
            print.log("_priceTotal.value",_priceTotal.value)

            print.log("---id", atualSalgadoAlterado.id)
            print.log("novo valor", atualSalgadoAlterado.amount)
           // print.log("novo valor", _chartList.value)
        }

        else{
            val atualCharList = _chartList.value ?: emptyList()

            _chartList.value = atualCharList + salgado //lista atual + o salgado
            print.log("id", salgado.id)
            print.log("amount", salgado.amount)

            val auxPrice = if(salgado.inOffer)salgado.priceInOffer else salgado.price
            _priceTotal.value += auxPrice
            print.log("_priceTotal.value",_priceTotal.value)

            //print.log("added to chartlist",_chartList.value!!)
        }
    }

    fun getTotalSalgados():Int{
        var total = 0
        _chartList.value?.forEach { salgado ->
            total += salgado.amount
        }

        return total;
    }
    fun getTotalPrice():Float{
         var total = 0F
        _chartList.value?.forEach { salgado ->
            val priceAux = if(salgado.inOffer)salgado.priceInOffer else salgado.price
            total += priceAux
        }

        return total;
    }

    fun addItemAdicional(itemAdicional: AdicionalDto){
        val atualAdicionaisList = _adicionais.value ?: emptyList()

        print.log("lista adicionais agora:", _adicionais.value )

        _adicionais.value = atualAdicionaisList + itemAdicional

        print.log("lista adicionais depois:", _adicionais.value )
    }
    fun removeItemAdicional(id:String){
        val atualAdicionaisList = _adicionais.value ?: emptyList()

        val newList:MutableList<AdicionalDto> = atualAdicionaisList.toMutableList()
        print.log("newList", newList)

        atualAdicionaisList.forEach {
            if(it.id == id) newList.remove(it)
        }

        _adicionais.value = newList

        print.log("lista adicionais depois:", _adicionais.value )
    }
}