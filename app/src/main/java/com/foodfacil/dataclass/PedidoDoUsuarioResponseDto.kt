package com.foodfacil.dataclass

import com.foodfacil.components.SalgadoResumidoResponseDto
import com.foodfacil.enums.PagamentoStatus
import com.foodfacil.enums.PedidoStatus
import com.foodfacil.enums.TipoDePagamento
import kotlinx.serialization.Serializable

@Serializable
data class PedidoDoUsuarioResponseDto(
    val id: String,
    val userId: String,
    val salgados: List<SalgadoResumidoResponseDto>,
    val acompanhamentos: List<AcompanhamentoResumidoResponseDto>,
    val endereco: AddressDto,
    val pagamentoEscolhido: TipoDePagamento,
    val quantiaReservada: Float,
    val total: Float,
    val createdAt: Long,
    val status: PedidoStatus,
    val pagamentoStatus: PagamentoStatus
)
