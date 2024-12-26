package com.amver.cultura_ayacucho.data.api

import com.amver.cultura_ayacucho.data.model.favorite.CreatePlaceFavoriteResponse
import com.amver.cultura_ayacucho.data.model.favorite.DeletePlaceFavoriteResponse
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiFavorite {

    //Obtener los lugares favoritos
    @Headers("Content-Type: application/json")
    @GET("favority/user")
    suspend fun getFavoritePlaces(
        @Query("username") username: String,
        @Header("Authorization") bearerToken: String
    ): List<PlacesItem>

    //Agregar un lugar a favoritos
    @Headers("Content-Type: application/json")
    @POST("favority")
    suspend fun addFavoritePlace(
        @Query("username") username: String,
        @Query("placeId") placeId: Int,
        @Header("Authorization") bearerToken: String
    ): CreatePlaceFavoriteResponse

    //Eliminar un lugar de favoritos
    @Headers("Content-Type: application/json")
    @DELETE("favority")
    suspend fun deleteFavoritePlace(
        @Query("username") username: String,
        @Query("placeId") placeId: Int,
        @Header("Authorization") bearerToken: String
    ): DeletePlaceFavoriteResponse
}