package com.amver.cultura_ayacucho.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequestUser(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
)
