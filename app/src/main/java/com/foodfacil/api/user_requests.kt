package com.foodfacil.api

import android.content.Context
import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.*
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.utils.UploadFile
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun updateProfilePicture(
    imageSelected: String,
    token: String?,
    userId: String?,
    context: Context
){
    val print = Print("CuponsViewModel")

    val uploadFileData = UploadFile().uploadFileToFirebaseStorage(imageSelected)
    val imageUrl = uploadFileData?.imageUri
    val imageRef = uploadFileData?.fileRef

    print.log("imageUrl",imageUrl)

    try {
        val response = httpClient.post("$baseUrl/user/update-photo") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            setBody(ProfilePhotoDto(userId.toString(), imageUrl.toString()))
        }
        print.log("sucesso",response.body())
        StoreUserData(context).savePhoto(imageUrl.toString());
    }catch (e:Exception){
        print.log("erro", e.message)
        UploadFile().deleteFile(imageRef!!)
    }
}

suspend fun getAllCuponsOfUser(token:String,userId:String): CupomsOfUserResponseDto {
    val print = Print("CuponsViewModel")
    try {
        val response = httpClient.get("$baseUrl/user/cupoms/$userId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
        print.log("sucesso",response.body())
        val list = json.decodeFromString<CupomsOfUserResponseDto>(response.body())
        print.log("cupons of user: $list")
        return list;
    }catch (e:Exception){
        print.log("erro", e.message)
        return CupomsOfUserResponseDto(emptyList())
    }
}

suspend fun cadastrarMe(body: UserAuthDto): String? {
    val print = Print("REQUESTS_CADASTRO")

    try {
        val response = httpClient.post("$baseUrl/auth/google-login") {
            io.ktor.http.headers {
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
        httpClient.close()
    }
    return null
}