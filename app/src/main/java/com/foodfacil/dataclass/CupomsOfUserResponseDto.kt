package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class CupomsOfUserResponseDto(val cupoms:List<SimpleCupomDto>)