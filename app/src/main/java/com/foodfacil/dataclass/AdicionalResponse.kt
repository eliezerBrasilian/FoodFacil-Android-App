package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AdicionalResponse(
    val lista: List<AdicionalDto>,
    val message: String
)