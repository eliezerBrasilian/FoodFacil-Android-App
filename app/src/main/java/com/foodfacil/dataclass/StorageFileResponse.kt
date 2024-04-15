package com.foodfacil.dataclass

import com.google.firebase.storage.StorageReference

data class StorageFileResponse(val imageUri:String,val fileRef: StorageReference)