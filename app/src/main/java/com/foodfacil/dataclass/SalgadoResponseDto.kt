package com.foodfacil.dataclass

import com.foodfacil.enums.Categoria
import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

@Serializable
public data class SalgadoResponseDto(
    val id: String,
    val name: String,
    val categoria: Categoria,
    val description: String,
    val price: Float,
    val image: String?,
    val inOffer: Boolean,
    val priceInOffer: Float,
    val disponibilidade: Disponibilidade,
    val acompanhamentos: List<Acompanhamento>
)