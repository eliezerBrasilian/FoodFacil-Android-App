package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class SimpleCupomDto(val id: String, val resgatado: Boolean, val used: Boolean)