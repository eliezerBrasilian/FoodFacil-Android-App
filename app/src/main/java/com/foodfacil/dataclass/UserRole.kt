package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole(s: String) {
    ADMIN("admin"),
    USER("user");
    private val role: String? = null
}