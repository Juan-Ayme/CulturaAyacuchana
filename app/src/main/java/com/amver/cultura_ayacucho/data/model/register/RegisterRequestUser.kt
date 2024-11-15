package com.amver.cultura_ayacucho.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequestUser(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("fullName") val fullName: String,
)
