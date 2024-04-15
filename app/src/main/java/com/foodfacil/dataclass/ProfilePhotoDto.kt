package com.foodfacil.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePhotoDto(val userUid: String, val newProfilePhoto: String)