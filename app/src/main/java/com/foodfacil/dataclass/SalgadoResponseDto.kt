package com.foodfacil.dataclass

import com.foodfacil.enums.Categoria
import com.foodfacil.enums.Disponibilidade
import kotlinx.serialization.Serializable

@Serializable
 data class SalgadoResponseDto(
    val id: String,
    val nome: String,
    val categoria: Categoria,
    val descricao: String,
    val preco: Float,
    val imagem: String?,
    val imagemRetangular:String?,
    val imagemQuadrada:String?,
    val emOferta: Boolean,
    val precoEmOferta: Float,
    val disponibilidade: Disponibilidade,
    val createdAt:Long,
    val observacao:String?,
    val ingredientes: List<IngredienteDto>
)