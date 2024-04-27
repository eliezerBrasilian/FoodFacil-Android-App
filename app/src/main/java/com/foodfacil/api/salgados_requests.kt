package com.foodfacil.api

import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.SalgadoResponseDto
import com.foodfacil.dataclass.SalgadosResponse
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun getAllSalgados(token:String): List<SalgadoResponseDto> {
    val print = Print()
    try {
        val response = httpClient.get("$baseUrl/salgado") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso",response.body())
        val salgadosResponse = json.decodeFromString<SalgadosResponse>(response.body())
        print.log("salgados: $salgadosResponse")
        return salgadosResponse.lista;
    }catch (e:Exception){
        print.log("erro", e.message)
        return emptyList()
    }
}



