package com.foodfacil.dataClass

import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable

@Serializable
data class AdicionalDto(
    val id:String,
    val imagem: String,
    val titulo: String,
    val descricao: String,
    val preco: Float,
    val disponibilidade: Disponibilidade
)