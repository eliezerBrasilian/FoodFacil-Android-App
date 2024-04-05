package com.foodfacil.viewModel

import androidx.lifecycle.ViewModel

class AcompanhamentosViewModel : ViewModel(){

    fun getAcompanhamentosList():List<String>{
        val acomp = listOf("Salgados Sortidos", "Bolinha de queijo", "Risoli de pizza", "Risoli de carme",
            "Salgados Sortidos", "Bolinha de queijo", "Risoli de pizza", "Risoli de carme",
            "Salgados Sortidos", "Bolinha de queijo", "Risoli de pizza", "Risoli de carme")

        return acomp;
    }
}