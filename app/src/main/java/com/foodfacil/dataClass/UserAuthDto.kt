package com.foodfacil.dataClass

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthDto (val email:String,
                        val password:String,
                        val name:String,
                        val profilePicture:String?,
                        val role:String = "USER")