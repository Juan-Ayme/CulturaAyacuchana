package com.amver.cultura_ayacucho.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseUser(
    @SerializedName("username") val username: String,
    @SerializedName("message") val message: String,
    @SerializedName("success") val sucess: Boolean,
    @SerializedName("token") val token: String,

)
