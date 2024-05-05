package com.foodfacil.api

import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.AdicionalDto
import com.foodfacil.dataclass.AdicionalResponse
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun getAllAdicionais(token:String): List<AdicionalDto> {
    val print = Print()
    try {
        val response = httpClient.get("$baseUrl/acompanhamento") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso acompanhamento",response.body())
        val list = json.decodeFromString<AdicionalResponse>(response.body())
        print.log("adicionais: $list")
        return list.lista;
    }catch (e:Exception){
        print.log("erro", e.message)
        return emptyList()
    }
}