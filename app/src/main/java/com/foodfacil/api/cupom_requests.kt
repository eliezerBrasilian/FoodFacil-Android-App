package com.foodfacil.api

import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.CuponsResponseDto
import com.foodfacil.dataclass.UserCupomDto
import com.foodfacil.services.Print
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun getAllCupons(token:String): CuponsResponseDto {
    val print = Print("getAllCupons")
    try {
        val response = httpClient.get("$baseUrl/cupom") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso",response.body())
        val list = json.decodeFromString<CuponsResponseDto>(response.body())
        print.log("cupons carregados: $list")
        return list;
    }catch (e:Exception){
        print.log("erro", e.message)
        return CuponsResponseDto(emptyList())
    }
}

suspend fun resgatarCupom(
    token: String?,
    cupomDto: UserCupomDto
):Boolean{
    val print = Print("CuponsViewModel")

    try {
        val response = httpClient.post("$baseUrl/user/cupom/add") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            setBody(cupomDto)
        }

        if(response.status.isSuccess()){
            print.log("sucesso",response.body())
            return true
        }
       else {
            print.log("status",response.status)
            print.log("message",response.body())
            return false
        }

    }catch (e:Exception){
        print.log("erro", e.message)
        return false
    }
}

