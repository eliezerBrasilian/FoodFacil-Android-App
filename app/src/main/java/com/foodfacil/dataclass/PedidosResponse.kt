package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class PedidosResponse(
    val lista: List<PedidoDoUsuarioResponseDto>,
    val message: String
)