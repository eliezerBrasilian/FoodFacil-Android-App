package com.foodfacil.dataClass

import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

@Serializable
data class SalgadoResponseDto(
    @NotNull val id: String,
    @NotNull val name: String,
    @NotNull val categoria: Categoria,
    @NotNull val description: String,
    @NotNull val price: Float,
    val image: String?,
    @NotNull val inOffer: Boolean,
    @NotNull val priceInOffer: Float,
    @NotNull val disponibilidade: Disponibilidade,
    @NotNull val acompanhamentos: List<Acompanhamento>
)