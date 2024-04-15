package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class UserCupomDto(val userId: String, val cupom: SimpleCupomDto)
