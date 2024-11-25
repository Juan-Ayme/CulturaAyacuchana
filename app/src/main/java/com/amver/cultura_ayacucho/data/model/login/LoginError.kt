package com.amver.cultura_ayacucho.data.model.login

import com.google.gson.annotations.SerializedName

// Primero, definimos la clase para el error de la API
data class LoginError(
    @SerializedName("path") val path: String,
    @SerializedName("message") val message: String,
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: List<String>
)