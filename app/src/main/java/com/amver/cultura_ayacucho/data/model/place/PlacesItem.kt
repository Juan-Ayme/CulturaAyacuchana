package com.amver.cultura_ayacucho.data.model.place

data class PlacesItem(
    val location: String,
    val name: String,
    val placeId: Int,
    val punctuationAverage: Double,
    val urlImage: String
)