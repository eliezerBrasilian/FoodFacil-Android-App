package com.foodfacil.dataClass

data class Salgado(val id:String,val title:String, val description:String,
                   val price:Float, val image: Int,
                   val inOffer:Boolean = false, val priceInOffer:Float = price,
    val isSalgadoNoCopo:Boolean = false
    )