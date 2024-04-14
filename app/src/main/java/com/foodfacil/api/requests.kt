package com.foodfacil.api

import com.foodfacil.dataClass.AdicionalDto
import com.foodfacil.dataClass.AdicionalResponse
import com.foodfacil.dataClass.PaymentData
import com.foodfacil.dataClass.SalgadoResponseDto
import com.foodfacil.dataClass.SalgadosResponse
import com.foodfacil.dataClass.UserAuthDto
import com.foodfacil.enums.Disponibilidade
import com.foodfacil.ktor.httpCLient
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

val ip = "192.168.100.31"
val baseUrl = "http://$ip:8080/food-facil/api/v1"

val json = Json { ignoreUnknownKeys = true }

suspend fun getAllAdicionais(token:String): List<AdicionalDto> {
    val print = Print("REQUESTS_GET_ALL_ADICIONAIS")
    try {
        val response = httpCLient.get("$baseUrl/adicional") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso",response.body())
        val list = json.decodeFromString<AdicionalResponse>(response.body())
        print.log("adicionais: $list")
        return list.lista;
    }catch (e:Exception){
        print.log("erro", e.message)
        return emptyList()
    }
}

suspend fun getAllSalgados(token:String): List<SalgadoResponseDto> {
    val print = Print("REQUESTS_GET_ALL_SALGADOS")
    try {
        val response = httpCLient.get("$baseUrl/salgado") {
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

//todo remover em um futuro proximo
 suspend fun cadastrarMe(body: UserAuthDto): String? {
    val print = Print("REQUESTS_CADASTRO")

    try {
        val response = httpCLient.post("$baseUrl/auth/google-login") {
            headers {
                contentType(ContentType.Application.Json)
            }
            setBody(body)
        }
        print.log("Request finalizada")

        if (response.status.isSuccess()) {
            val responseBody = response.body<Map<String, String>>()
            val token = responseBody["token"]
            print.log("Token: $token")
            return token
        } else {
            print.log(response.body())
            print.log("Erro na requisição: ${response.status}")
        }
    } catch (e: Exception) {
        print.log("Excessao na requisição: ${e.message}")
    } finally {
        httpCLient.close()
    }
    return null
}

suspend fun makePayment(body: PaymentData): String {
    val print = Print("REQUESTS_MAKE_PAYMENT")
    val response = httpCLient.post("http://192.168.100.31:8080/food-facil/api/payment/v1"){
        headers{
            contentType(ContentType.Application.Json)
        }
        setBody(body)
    }
    print.log("request finalizada")
    val responseBody = response.bodyAsText()
    print.log(responseBody)
    httpCLient.close()
    return responseBody
}