package com.foodfacil.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.api.ktor.baseUrl
import com.foodfacil.api.ktor.httpClient
import com.foodfacil.dataclass.UserAuthDto
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){
    private val TAG = "AUTHVIEWMODEL"

    private val print = Print(TAG)

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun googleSignIn(
        userUid: String,
        name:String, email:String,
        profilePicture: Uri?,
        context: Context,
        onSuccess:()->Unit = {},
        onError:()->Unit = {}
    ) = viewModelScope.launch{
        print.log("userUid",userUid)
       // _loading.value = true

        try {
            val response = httpClient.post("$baseUrl/auth/google-login") {
                headers {
                    contentType(ContentType.Application.Json)
                }
                setBody(UserAuthDto(
                    email = email,
                    password = "12345",
                    name = name,
                    profilePicture = profilePicture.toString()
                ))
            }
            print.log("Request finalizada")

            if (response.status.isSuccess()) {
                val responseBody = response.body<Map<String, String>>()
                val token = responseBody["token"]
                val userUid = responseBody["userUid"]
                val profilePicture = responseBody["profilePicture"]

                print.log("Token: $token")
                print.log("userUid: $userUid")

                val storeData = StoreUserData(context)
                    storeData.saveUid(userUid.toString())
                storeData.saveEmail(email)
                storeData.saveToken(token.toString())
                storeData.saveName(name)
                storeData.savePhoto( if(profilePicture == null)"" else profilePicture)

                onSuccess()

            } else {
                print.log(response.body())
                print.log("Erro na requisição: ${response.status}")
               // onError(Exception("Erro na requisição: ${response.status}"))
            }
        } catch (e: Exception) {
            print.log("Excessao na requisição: ${e.message}")
           // onError(e)
        }
    }


}

