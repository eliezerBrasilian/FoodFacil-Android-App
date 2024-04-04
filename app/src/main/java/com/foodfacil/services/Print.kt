package com.foodfacil.services

import android.util.Log

class Print(val TAG: String) {
    fun log(title:String, message: Any? = null){
         Log.e(TAG,"$title : ${message.toString()}")
    }
    fun log(message: Any?){
       Log.e(TAG, message.toString())

    }
}