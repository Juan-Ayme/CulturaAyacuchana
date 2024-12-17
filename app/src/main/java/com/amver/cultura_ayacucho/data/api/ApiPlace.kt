package com.amver.cultura_ayacucho.data.api;

import com.amver.cultura_ayacucho.data.model.place.Places;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;

interface ApiPlace {
    //class Places : ArrayList<PlacesItem>()
    @Headers("Content-Type: application/json")
    @GET("places/popular")
    suspend fun getPlacesPopular(): Response<Places>

}
