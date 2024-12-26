package com.amver.cultura_ayacucho.data.api;

import com.amver.cultura_ayacucho.data.model.place.Place
import com.amver.cultura_ayacucho.data.model.place.PlacesItem

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header
import retrofit2.http.Headers;
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPlace {

    //Encontrar lugares populares
    @Headers("Content-Type: application/json")
    @GET("place/popular")
    suspend fun getPlacesPopular(): List<PlacesItem>

    //Encontrar lugares por Id
    @Headers("Content-Type: application/json")
    @GET("place/{placeId}")
    suspend fun getPlaceDetail(
        @Path("placeId") placeId: Int
    ): Place

    //Encontrar lugares por categoria
    @Headers("Content-Type: application/json")
    @GET("place/filter/{category}")
    suspend fun getPlacesByCategory(
        @Path("category") category: String
    ): List<PlacesItem>

    //Buscar Lugares
    @Headers("Content-Type: application/json")
    @GET("place/search")
    suspend fun searchPlaces(
        @Query("keyword") keyword: String
    ): List<PlacesItem>

    //Encontrar lugares por provincia y categoria
    @GET("place/category/{category}/province/{province}")
    suspend fun getPlacesByCategoryAndProvince(
        @Path("category") category: String,
        @Path("province") province: String
    ): List<PlacesItem>

    //Encontrar lugares solo por provincia
    @GET("place")
    suspend fun getPlacesByProvince(
        @Query("province") province: String
    ): List<PlacesItem>
}
