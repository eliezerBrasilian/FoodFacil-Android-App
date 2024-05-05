package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class TokenDoDispositivoRequestDto(
     val token:String,
     val userId:String
)
