package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class SimplesSalgado(val id:String, val observacao:String, val sabores:List<String>,  val quantidade:Int)
