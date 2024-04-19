package com.foodfacil.dataclass

import com.foodfacil.enums.Categoria
import com.foodfacil.enums.Disponibilidade
data class SalgadoDto(
    val id: String,
    val name: String,
    val categoria: Categoria,
    val description: String,
    val price: Float,
    val image: String?,
    val imageRetangular:String?,
    val imageQuadrada:String?,
    val inOffer: Boolean,
    val priceInOffer: Float,
    val disponibilidade: Disponibilidade,
    val acompanhamentos: List<Acompanhamento>
)