package com.foodfacil.dataclass

import com.foodfacil.enums.CupomCategory
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class CuponsResponseDto(
  val cupoms:List<CupomResponseItemDto>
)

@Serializable
data class CupomResponseItemDto(
  val id: String,
  val code: String,
  val porcentoDeDesconto: Int,
  @Contextual
  val createdAt: Instant,
  @Contextual
  val expirationDate: Instant,
  val expired: Boolean,
  val description: String,
  val cupomCategory: CupomCategory,
)