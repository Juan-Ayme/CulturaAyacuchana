package com.amver.cultura_ayacucho.data.model.favorite

import com.google.gson.annotations.SerializedName

data class CreatePlaceFavoriteResponse(
    @SerializedName("favoriteId") val favoriteId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("placeId") val placeId: Int,
    @SerializedName("savedDate") val savedDate: String,
)