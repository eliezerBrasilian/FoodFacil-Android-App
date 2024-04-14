package com.foodfacil.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Disponibilidade(val value: String) {
    DISPONIVEL("Disponivel"),
    INDISPONIVEL("INDISPONIVEL")
}
