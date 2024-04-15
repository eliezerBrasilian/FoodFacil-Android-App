package com.foodfacil.dataclass;

import com.foodfacil.enums.CupomCategory
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CupomDto(
        val id:String,
        val code: String,
        val porcentoDeDesconto: Int,
        @Contextual
        val createdAt: Instant,
        @Contextual
        val expirationDate: Instant,
        val expired: Boolean,
        var used: Boolean,
        val description: String,
        val cupomCategory: CupomCategory,
        var resgatado:Boolean
)
