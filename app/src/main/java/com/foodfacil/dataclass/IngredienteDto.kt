package com.foodfacil.dataclass

import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable

@Serializable
data class IngredienteDto(val nome:String,
                          val imagem:String?,
                          val preco:Float = 0f,
                          val createdAt:Long,
                          val disponibilidade:Disponibilidade
    )
