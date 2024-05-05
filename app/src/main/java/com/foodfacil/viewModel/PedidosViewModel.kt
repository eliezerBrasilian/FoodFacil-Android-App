package com.foodfacil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.getPedidos
import com.foodfacil.dataclass.PedidoDoUsuarioResponseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PedidosViewModel:ViewModel() {
    private val _pedidos = MutableStateFlow<List<PedidoDoUsuarioResponseDto>>(emptyList())
    val pedidos: MutableStateFlow<List<PedidoDoUsuarioResponseDto>> = _pedidos

    fun getAllPedidos(token: String, userId:String) = viewModelScope.launch{
        val pedidosList = getPedidos(token, userId);

        pedidos.value = pedidosList;
    }

}