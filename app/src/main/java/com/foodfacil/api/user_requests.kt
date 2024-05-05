package com.foodfacil.api

import android.content.Context
import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.api.ktor.json
import com.foodfacil.dataclass.AddressDto
import com.foodfacil.dataclass.CupomsOfUserResponseDto
import com.foodfacil.dataclass.ProfilePhotoDto
import com.foodfacil.dataclass.TokenDoDispositivoRequestDto
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

val print = Print()

suspend fun salvaTokenDoDispositivo(tokenDoDispositivoRequestDto: TokenDoDispositivoRequestDto,
                                    userToken:String){
    try {
        val response = httpClient.post("$baseUrl/user/token-dispositivo") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $userToken")
            setBody(tokenDoDispositivoRequestDto)
        }
        print.log("sucesso, token salvo/atualizado---------",response.body())
        val responseBody = response.body<Map<String,String>>()
        val message = responseBody["message"]
        print.log(message)
    }catch (e:Exception){
        print.log("erro ao salvar/atualizar token do dispositivo",e.message)
    }
}

suspend fun updateAddress(
    addressDto: AddressDto,
    token: String?,
    userId: String?,
): Boolean {
    val print = Print()

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



