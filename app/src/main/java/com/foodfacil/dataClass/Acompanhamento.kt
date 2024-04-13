package com.foodfacil.dataClass

import kotlinx.serialization.Serializable

@Serializable
data class Acompanhamento(val id:String = "",
                          val name:String,
                          val precoPorUnidade:Float = 0f,
                            val disponibilidade:String = ""
    )