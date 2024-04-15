package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class PaymentData( val deviceToken:String, val userName:String, val userPhone:String)