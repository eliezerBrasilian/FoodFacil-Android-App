package com.foodfacil.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.Salgado
import com.foodfacil.services.Print

class ChartViewModel : ViewModel(){
    private val _chartList = MutableLiveData<List<Salgado>>(emptyList())
    val chartList:LiveData<List<Salgado>> = _chartList

    val _adicionais = MutableLiveData<List<AdicionalDto>>(emptyList())
    val adicionais:LiveData<List<AdicionalDto>> = _adicionais

    val print = Print("CHARTVIEWMODEL")

    fun increment(salgadoId: String){
        val salgadoFounded = _chartList.value?.find { salgado -> salgadoId == salgado.id }
        print.log("---encontrado",salgadoFounded)

        val atualChartList = (_chartList.value?: emptyList()).toMutableList()
        val index = atualChartList.indexOf(salgadoFounded)

        val atualAmount = atualChartList[index].amount
        var atualPrice: Float;

        atualPrice = if(salgadoFounded?.inOffer == true){
            atualChartList[index].priceInOffer
        }else{
            atualChartList[index].newPriceAux
        }

        val newAmount = atualAmount + 1
        val newPrice = atualPrice * newAmount

        val atualSalgadoAlterado =  atualChartList[index].copy(
            amount =  newAmount,
            newPriceAux = newPrice
            )

         atualChartList[index] = atualSalgadoAlterado
        _chartList.value = atualChartList //lista atual + o salgado

        print.log("salgado alterado",atualChartList[index])
    }

    fun decrement(salgadoId: String){
        val salgadoFounded = _chartList.value?.find { salgado -> salgadoId == salgado.id }
        print.log("---encontrado",salgadoFounded)

        val atualChartList = (_chartList.value?: emptyList()).toMutableList()
        val index = atualChartList.indexOf(salgadoFounded)

        val atualAmount = atualChartList[index].amount
        var atualPrice: Float;

        atualPrice = if(salgadoFounded?.inOffer == true){
            atualChartList[index].priceInOffer
        }else{
            atualChartList[index].newPriceAux
        }
        //logica
        val newAmount = atualAmount - 1
        val newPrice = atualPrice * newAmount

        print.log("atual amount", newAmount)
        if(newAmount == 0){
            //remove
            print.log("vazio")
            atualChartList.removeAt(index)
            _chartList.value = atualChartList

           /* val contem = _chartList.value.contains(salgadoFounded)*/
        }else{
            //só atualiza
            val atualSalgadoAlterado =  atualChartList[index].copy(
                amount =  newAmount,
                newPriceAux = newPrice
            )

            atualChartList[index] = atualSalgadoAlterado
            _chartList.value = atualChartList //lista atual + o salgado

            print.log("salgado alterado",atualChartList[index])
        }
    }

    fun findPrice(salgadoId:String): Float {
        print.log("id recebido",salgadoId)
        //encontrar o salgado
        val salgadoFounded = _chartList.value?.find { salgado -> salgadoId == salgado.id }
        print.log("---encontrado",salgadoFounded)

        val atualCharList = (_chartList.value?: emptyList()).toMutableList()
        val index = atualCharList.indexOf(salgadoFounded)
        print.log("index: ", index)

        return atualCharList[index].newPriceAux
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

            print.log("---id", atualSalgadoAlterado.id)
            print.log("novo valor", atualSalgadoAlterado.amount)
           // print.log("novo valor", _chartList.value)
        }

        else{
            val atualCharList = _chartList.value ?: emptyList()

            _chartList.value = atualCharList + salgado //lista atual + o salgado
            print.log("id", salgado.id)
            print.log("amount", salgado.amount)
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
            total += salgado.newPriceAux
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