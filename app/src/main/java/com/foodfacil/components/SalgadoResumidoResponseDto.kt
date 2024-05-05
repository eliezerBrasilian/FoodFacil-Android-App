package com.foodfacil.components

import kotlinx.serialization.Serializable

@Serializable
data class SalgadoResumidoResponseDto(
    val nome: String,
    val imagem: String,
    val preco: Float,
    val observacao: String,
    val quantidade: Int,
    val sabores: List<String>
)
