package com.foodfacil.services

import android.util.Log

class Print(val TAG: String) {
    fun log(title:Any?, message: Any? = null){
        if(title == null){
            Log.e(TAG, message.toString())
        }else{
            Log.e(TAG,"$title : $message")
        }
    }
}