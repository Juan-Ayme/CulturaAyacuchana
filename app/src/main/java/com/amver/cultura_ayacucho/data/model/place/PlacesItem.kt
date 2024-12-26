package com.amver.cultura_ayacucho.data.model.place

import com.google.gson.annotations.SerializedName

data class PlacesItem(
    @SerializedName("placeId") val placeId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String?,
    @SerializedName("province") val province: String?,
    @SerializedName("urlImage") val urlImage: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("punctuationAverage") val punctuationAverage: Double?,
    @SerializedName("distance") val distance: String?,
    val isFavorite: Boolean? = false
){
    // Constructor secundario para copiar con estado de favorito
    fun copyWithFavorite(isFavorite: Boolean): PlacesItem {
        return PlacesItem(
            placeId = this.placeId,
            name = this.name,
            province = this.province,
            description = this.description,
            urlImage = this.urlImage,
            punctuationAverage = this.punctuationAverage,
            location = this.location,
            distance = this.distance,
            isFavorite = isFavorite
        )
    }
}