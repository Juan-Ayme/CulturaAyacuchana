package com.amver.cultura_ayacucho.data.model.place

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("descripcion") val description: String,
    val images: List<Any>,
    val location: String,
    val name: String,
    val placeId: Int,
    val qualificationAverage: Double,
)