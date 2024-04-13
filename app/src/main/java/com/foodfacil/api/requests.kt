package com.foodfacil.api

import com.foodfacil.dataClass.PaymentData
import com.foodfacil.dataClass.UserAuthDto
import com.foodfacil.ktor.httpCLient
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
val ip = "192.168.100.31"
val baseUrl = "http://$ip:8080/food-facil/api/v1"

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