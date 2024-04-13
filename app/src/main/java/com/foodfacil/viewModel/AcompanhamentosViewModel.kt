package com.foodfacil.viewModel

import androidx.lifecycle.ViewModel
import com.foodfacil.dataClass.Acompanhamento

class AcompanhamentosViewModel : ViewModel(){


    fun getAcompanhamentosList():List<Acompanhamento>{
        //val acomp = listOf(, "Bolinha de queijo", "Risoli de pizza", "Risoli de carme")
        val acomp = listOf<Acompanhamento>(
            Acompanhamento(name ="Salgados Sortidos", precoPorUnidade = 1f),
            Acompanhamento(name = "Bolinha de queijo",precoPorUnidade = 1f),
            Acompanhamento(name = "Risoli de pizza",precoPorUnidade = 1f),
            Acompanhamento(name = "Risoli de carne",precoPorUnidade = 1f),
            )
        return acomp;
    }
}