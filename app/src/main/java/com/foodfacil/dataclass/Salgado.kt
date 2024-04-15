package com.foodfacil.dataclass

data class Salgado(
    val id:String,
    val title:String,
    val description:String,
    val price:Float, val image: String?,
    val inOffer:Boolean = false,
    val priceInOffer:Float = price,
    var newPriceAux:Float = price,
    val isSalgadoNoCopo:Boolean = false,
    var amount:Int = 0,
    val acompanhamentos:List<Acompanhamento> = emptyList(),
    ){
    override fun equals(other: Any?): Boolean {
        return if (other is Salgado) {
            id == other.id && title == other.title && description == other.description &&
                    price == other.price && image == other.image && inOffer == other.inOffer &&
                    priceInOffer == other.priceInOffer && isSalgadoNoCopo == other.isSalgadoNoCopo &&
                    acompanhamentos == other.acompanhamentos && newPriceAux == other.newPriceAux
        } else {
            false
        }
    }
}