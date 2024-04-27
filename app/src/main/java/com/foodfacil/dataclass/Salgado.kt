package com.foodfacil.dataclass

import com.foodfacil.enums.Categoria
import com.foodfacil.enums.Disponibilidade

data class Salgado(
    val id:String,
    val nome:String,
    val categoria:Categoria,
    val descricao:String,
    val preco: Float,
    val imagem: String?,
    val imagemRetangular:String?,
    val imagemQuadrada:String?,
    val emOferta: Boolean,
    val precoEmOferta: Float,
    val disponibilidade: Disponibilidade,
    val sabores: List<String>,
    var amount:Int = 0,
    var observacao:String = "",
    ){
    override fun equals(other: Any?): Boolean {
        return if (other is Salgado) {
            id == other.id &&
                    nome == other.nome &&
                    categoria == other.categoria &&
                    descricao == other.descricao &&
                    preco == other.preco &&
                    imagem == other.imagem &&
                    imagemRetangular == other.imagemRetangular &&
                    imagemQuadrada == other.imagemQuadrada &&
                    emOferta == other.emOferta &&
                    precoEmOferta == other.precoEmOferta &&
                    disponibilidade == other.disponibilidade &&
                    sabores == other.sabores &&
                    amount == other.amount &&
                    observacao == other.observacao
        } else {
            false
        }
    }
}