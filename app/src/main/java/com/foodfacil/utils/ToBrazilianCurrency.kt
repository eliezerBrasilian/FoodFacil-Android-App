package com.foodfacil.utils

import java.text.NumberFormat
import java.util.Locale

fun toBrazilianCurrency(valor: Float): String {
    val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatoMoeda.format(valor)
}