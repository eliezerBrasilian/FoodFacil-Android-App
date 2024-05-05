package com.foodfacil.dataclass

import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable

@Serializable
data class AdicionalDto(
    val id:String,
    val nome: String,
    val descricao: String,
    val preco: Float,
    val imagem: String,
    val disponibilidade: Disponibilidade,
    val createdAt: Long
)