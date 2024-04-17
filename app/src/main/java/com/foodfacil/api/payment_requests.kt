package com.foodfacil.api

import com.foodfacil.api.ktor.httpClient
import com.foodfacil.dataclass.PaymentData
import com.foodfacil.services.Print
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers

suspend fun makePayment(body: PaymentData): String {
    val print = Print("REQUESTS_MAKE_PAYMENT")
    val response = httpClient.post("http://192.168.100.31:8080/food-facil/api/payment/v1"){
        headers {
            contentType(ContentType.Application.Json)
        }
        setBody(body)
    }
    print.log("request finalizada")
    val responseBody = response.bodyAsText()
    print.log(responseBody)
    httpClient.close()
    return responseBody
}