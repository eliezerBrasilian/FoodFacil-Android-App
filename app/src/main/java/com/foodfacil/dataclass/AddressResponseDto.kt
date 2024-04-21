package com.foodfacil.dataclass

public data class AddressResponseDto(val cidade:String = "Brás Pires",
                                     val rua: String?,
                                     val bairro: String?,
                                     val numero: String?,
                                     val complemento:String? = null )


/*
      String cidade,
        String rua,
        int numero,
        String bairro,
        String complemento
 */