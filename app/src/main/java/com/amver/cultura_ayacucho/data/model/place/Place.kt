package com.amver.cultura_ayacucho.data.model.place

data class Place(
    val descripcion: String,
    val images: List<Any>,
    val location: String,
    val name: String,
    val placeId: Int,
    val qualificationAverage: Int
)