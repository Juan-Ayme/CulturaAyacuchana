package com.amver.cultura_ayacucho.data.model.place

import com.google.gson.annotations.SerializedName

data class PlacesItem(
    @SerializedName("placeId") val placeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("urlImage") val urlImage: String,
    @SerializedName("punctuationAverage") val punctuationAverage: Double,
    @SerializedName("distance") val distance: String?
)