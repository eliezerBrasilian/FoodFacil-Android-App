package com.foodfacil.api.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val localHost = "http://192.168.0.105:8080/food-facil/api/v1"
val prodHost = "https://foodfacil.site/food-facil/api/v1"

val baseUrl = localHost
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
