package com.foodfacil.viewModel


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamestate.enums.Collections.Companion.USERS
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel(){
    private val firestore = Firebase.firestore
    private val TAG = "AUTHVIEWMODEL"


    fun isLogged():Boolean{
        return false;
    }

    fun login(emailInput: String, passwordInput: String, context: Context, function: () -> Unit) {

    }

    fun createUserWithEmailAndPassword(emailInput: String, passwordInput: String, nameInput: String, context: Context, function: () -> Unit) {

    }

    fun createUserAfterGoogleSignIn(userUid:String,userToken:String,name:String, email:String, profilePicture:String?) = viewModelScope.launch{
       val userExists = userExistsOnFirebase(userUid)

        val newUserData = mapOf("name" to name, "email" to email, "address" to null,
            "profilePicture" to profilePicture, "uid" to userUid, "userToken" to userToken,
            "deviceUid" to null, "amountOfMoneySpentTotal" to 0, "amountOfItemsBoughtTotal" to 0,
            "amountOfCouponsUsed" to 0)

        if(!userExists){
            firestore.collection(USERS).add(newUserData).
            await()
        }
    }
    private suspend fun userExistsOnFirebase(userUid: String):Boolean{
        try {
            val documentFounded = firestore.collection(USERS).whereEqualTo("uid", userUid)
                .get().await()
            return true;
        }
        catch (e:Exception){
            Log.e(TAG, "error: ${e.message}")
            return false;
        }
    }


}