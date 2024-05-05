package com.foodfacil.api

import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.Pedido
import com.foodfacil.dataclass.PedidoDoUsuarioResponseDto
import com.foodfacil.dataclass.PedidosResponse
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend fun registraPedido(token:String,
                           pedido: Pedido, onSuccess:(id: String?)->Unit = {}) {
    val print = Print()
    try {
        val response = httpClient.post("$baseUrl/user/pedido") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            setBody(pedido)
        }
        print.log("sucesso",response.body())
        val responseBody = response.body<Map<String,String>>()
        val message = responseBody["message"]
        val id = responseBody["id"]
        print.log(message)

       onSuccess(id)
    }catch (e:Exception){
        print.log("erro", e.message)

    }
}

suspend fun getPedidos(token: String, userId:String): List<PedidoDoUsuarioResponseDto> {
    val print = Print()
    try {
        val response = httpClient.get("$baseUrl/user/pedidos/$userId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso",response.body())

        val responsta = json.decodeFromString<PedidosResponse>(response.body())
        print.log("pedidos: $responsta")
        return responsta.lista;
    }catch (e:Exception){
        print.log("erro", e.message)
        return emptyList()
    }

}



