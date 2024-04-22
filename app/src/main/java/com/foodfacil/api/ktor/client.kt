package com.foodfacil.api.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val localIp = "192.168.0.105"
val producaoIp = "191.252.92.39"

val ip = localIp
val baseUrl = "http://$ip:8080/food-facil/api/v1"
val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }

val httpClient = HttpClient(Android){
    install(Logging){
        level = LogLevel.ALL
    }
    install(ContentNegotiation){
        json(Json{
          ignoreUnknownKeys = true
            coerceInputValues = true
        })
    }
}
