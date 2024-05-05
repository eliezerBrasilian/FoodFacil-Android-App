package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AcompanhamentoResumidoResponseDto(
    val nome: String,
    val descricao: String,
    val preco: Float,
    val quantidade: Int
)
