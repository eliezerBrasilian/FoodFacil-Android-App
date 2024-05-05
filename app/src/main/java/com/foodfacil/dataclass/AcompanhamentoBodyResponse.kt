package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class AcompanhamentoBodyResponse(
    val lista: List<AcompanhamentoResponseDto>,
    val message: String
)