package com.foodfacil.viewModel

import androidx.lifecycle.ViewModel
import com.foodfacil.dataClass.Acompanhamento

class AcompanhamentosViewModel : ViewModel(){


    fun getAcompanhamentosList():List<Acompanhamento>{
        //val acomp = listOf(, "Bolinha de queijo", "Risoli de pizza", "Risoli de carme")
        val acomp = listOf<Acompanhamento>(
            Acompanhamento("Salgados Sortidos",1f),
            Acompanhamento("Bolinha de queijo",1f),
            Acompanhamento("Risoli de pizza",1f),
            Acompanhamento("Risoli de carne",1f),
            )
        return acomp;
    }
}