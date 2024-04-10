package com.foodfacil.api

import com.foodfacil.dataClass.PaymentData
import com.foodfacil.ktor.httpCLient
import com.foodfacil.services.Print
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.client.statement.request
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers

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