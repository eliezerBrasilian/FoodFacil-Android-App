package com.foodfacil.dataclass

data class RegisterDto(val email: String, val password: String, val role: UserRole, val name: String) {}