package com.amver.cultura_ayacucho.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponseUser(
    @SerializedName("userId") val userId: Int,
    @SerializedName("email") val email: String,
    @SerializedName("fullName") val fullName: String,
)
