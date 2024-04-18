package com.foodfacil.api.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val ip = "192.168.0.105"
val baseUrl = "http://$ip:8080/food-facil/api/v1"
val json = Json { ignoreUnknownKeys = true }

val httpClient = HttpClient(Android){
    install(Logging){
        level = LogLevel.ALL
    }
    install(ContentNegotiation){
        json(Json{
          ignoreUnknownKeys = true
        })
    }
}
