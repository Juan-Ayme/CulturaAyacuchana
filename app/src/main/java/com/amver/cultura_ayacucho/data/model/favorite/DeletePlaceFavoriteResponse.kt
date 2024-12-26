package com.amver.cultura_ayacucho.data.model.favorite

import com.google.gson.annotations.SerializedName

data class DeletePlaceFavoriteResponse(
    @SerializedName("message")val message: String,
    @SerializedName("success") val success: Boolean
)
