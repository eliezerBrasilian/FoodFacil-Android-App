package com.foodfacil.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel(){

    fun isLogged():Boolean{
        return false;
    }

    fun login(emailInput: String, passwordInput: String, context: Context, function: () -> Unit) {

    }

    fun createUserWithEmailAndPassword(emailInput: String, passwordInput: String, nameInput: String, context: Context, function: () -> Unit) {

    }

}