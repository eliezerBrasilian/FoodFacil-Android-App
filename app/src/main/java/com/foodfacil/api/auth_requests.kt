package com.foodfacil.api

import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.dataclass.LoginAuthDto
import com.foodfacil.dataclass.UserAuthDto
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

suspend fun loginMe(body: LoginAuthDto, getUserData: (token: String?, userId: String?, name: String?, profilePhoto: String?, errorMessage:String?) -> Unit) {
    val print = Print("AUTHVIEWMODEL")

    try {
        val response = httpClient.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        print.log("Request finalizada")

        if (response.status.isSuccess()) {
            val responseBody = response.body<Map<String, String>>()
            print.log("responseBody", responseBody)
            val token = responseBody["token"]
            val userId = responseBody["userId"]
            val profilePhoto = responseBody["profilePicture"]
            val name = responseBody["name"]
            print.log("Token: $token")

            getUserData(token.toString(), userId.toString(), name.toString(), profilePhoto,null)
        } else {
            print.log(response.body<Map<String,String>>())
            val responseBody = response.body<Map<String,String>>()
            val errorMessage = responseBody["message"]
            print.log("Erro na requisição: ${response.status}")
            getUserData(null, null,null,null, errorMessage)
        }
    } catch (e: Exception) {
        print.log("Excessao na requisição: ${e.message}")
        getUserData(null, null,null,null, e.message)
    }
}

suspend fun cadastrarMe(body: UserAuthDto, getUserData: (token: String?, userId: String?) -> Unit) {
    val print = Print("AUTHVIEWMODEL")

    try {
        val response = httpClient.post("$baseUrl/auth/sign-up") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        print.log("Request finalizada")

        if (response.status.isSuccess()) {
            val responseBody = response.body<Map<String, String>>()
            print.log("responseBody", responseBody)
            val token = responseBody["token"]
            val userId = responseBody["userId"]
            print.log("Token: $token")

            getUserData(token, userId)
        } else {
            print.log(response.body<Map<String,String>>())
            print.log("Erro na requisição: ${response.status}")
            getUserData(null, null)
        }
    } catch (e: Exception) {
        print.log("Excessao na requisição: ${e.message}")
        getUserData(null, null)
    }
}