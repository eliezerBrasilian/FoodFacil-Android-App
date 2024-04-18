package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val cidade:String,
    val rua:String,
    val numero:Int,
    val bairro:String,
    val complemento:String
)
