package com.foodfacil.dataclass

data class Salgado(
    val id:String,
    val title:String,
    val description:String,
    val price:Float,
    val image: String?,
    val imageRetangular:String?,
    val imageQuadrada:String?,
    val inOffer:Boolean,
    val priceInOffer:Float,
    var amount:Int = 0,
    val acompanhamentos:List<IngredienteDto> = emptyList(),
    var observacao:String = "",
    ){
    override fun equals(other: Any?): Boolean {
        return if (other is Salgado) {
            id == other.id && title == other.title && description == other.description &&
                    price == other.price && image == other.image && inOffer == other.inOffer &&
                    priceInOffer == other.priceInOffer &&
                    amount == other.amount &&
                    acompanhamentos == other.acompanhamentos
                    && imageRetangular == other.imageRetangular
                    && imageQuadrada == other.imageQuadrada
                    && observacao == other.observacao
        } else {
            false
        }
    }
}