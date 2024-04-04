package com.foodfacil.viewModel


import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodfacil.services.Print
import com.foodfacil.enums.Collections.Companion.USERS
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel(){
    private val firestore = Firebase.firestore
    private val TAG = "AUTHVIEWMODEL"

    private val print = Print(TAG)

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun isLogged():Boolean{
        return false;
    }

    fun login(emailInput: String, passwordInput: String, context: Context, function: () -> Unit) {

    }

    fun createUserWithEmailAndPassword(emailInput: String, passwordInput: String, nameInput: String, context: Context, function: () -> Unit) {

    }

    fun createUserAfterGoogleSignIn(
        userUid: String,
        userToken:String,
        name:String, email:String, profilePicture: Uri?,
        onSuccess:()->Unit = {},
        onError:()->Unit = {}
    ) = viewModelScope.launch{
        print.log("userUid",userUid)
       // _loading.value = true
       val userExists = userExistsOnFirebase(userUid)
        print.log("user exists",userExists)

        val newUserData = mapOf("name" to name, "email" to email, "address" to null,
            "profilePicture" to profilePicture, "uid" to userUid, "userToken" to userToken,
            "deviceUid" to null, "amountOfMoneySpentTotal" to 0, "amountOfItemsBoughtTotal" to 0,
            "amountOfCouponsUsed" to 0)

        if(!userExists){
            firestore.collection(USERS).add(newUserData).addOnSuccessListener { docRef->
                print.log("user added",docRef.id)
                onSuccess()
            }
        }
        else {
            print.log("logado com sucesso")
            onSuccess()
        }
    }
    private suspend fun userExistsOnFirebase(userUid: String):Boolean{
        try {
            val documentFounded = firestore.collection(USERS).whereEqualTo("uid", userUid)
                .get().await()
            print.log("documentFounded",documentFounded.size())
            _loading.value = false
            return documentFounded.size() > 0
        }
        catch (e:Exception){
            print.log(TAG, "error: ${e.message}")
            _loading.value = false
            return false;
        }
    }

}

