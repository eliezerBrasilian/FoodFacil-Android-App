package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class LoginAuthDto(val email:String, val password:String)