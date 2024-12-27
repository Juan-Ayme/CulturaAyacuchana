package com.amver.cultura_ayacucho.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterErrorUSer (
    @SerializedName("path") val path: String,
    @SerializedName("message") val message: String,
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: List<String>
)