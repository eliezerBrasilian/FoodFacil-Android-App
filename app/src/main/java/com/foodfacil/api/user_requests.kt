package com.foodfacil.api

import android.content.Context
import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.dataclass.CupomsOfUserResponseDto
import com.foodfacil.dataclass.LoginAuthDto
import com.foodfacil.dataclass.ProfilePhotoDto
import com.foodfacil.dataclass.UserAuthDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.foodfacil.services.UploadFile
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

suspend fun updateAddress(
    addressDto: AddressDto,
    token: String?,
    userId: String?,
): Boolean {
    val print = Print("USERVIEWMODEL")

    try {
        val response = httpClient.post("$baseUrl/user/address/update/${userId}") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            setBody(addressDto)
        }
        print.log("sucesso",response.body())
        val responseBody = response.body<Map<String,String>>()
        val message = responseBody["message"]
        print.log(message)

        if(message == "endereço atualizado"){
            return true
        }

        else return false

    }catch (e:Exception){
        print.log("erro", e.message)
        return false
    }
}
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