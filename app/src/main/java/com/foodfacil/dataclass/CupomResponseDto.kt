package com.foodfacil.dataclass

import kotlinx.serialization.Serializable


@Serializable
data class CupomResponseDto(
  val cupoms:List<CupomDto>
)
