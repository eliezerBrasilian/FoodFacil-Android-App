package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole{
    ADMIN,
    USER;
}