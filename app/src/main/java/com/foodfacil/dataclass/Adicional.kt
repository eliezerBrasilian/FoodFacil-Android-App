package com.foodfacil.dataclass

import com.foodfacil.enums.Disponibilidade

data class Adicional(
    val id: String,
    val imagem: String,
    val titulo: String,
    val descricao: String,
    val preco: Float,
    val disponibilidade: Disponibilidade,
    var amount: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Adicional) {
            id == other.id
                    && imagem == other.imagem
                    && titulo == other.titulo
                    && descricao == other.descricao
                    && preco == other.preco
                    && disponibilidade == other.disponibilidade
                    && amount == other.amount
        } else {
            false
        }
    }
}