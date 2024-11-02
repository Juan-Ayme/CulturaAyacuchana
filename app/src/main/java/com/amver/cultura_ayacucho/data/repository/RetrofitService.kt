package com.amver.cultura_ayacucho.data.repository

import com.amver.cultura_ayacucho.data.model.MovieDetail
import com.amver.cultura_ayacucho.data.model.MovieModel
import com.amver.cultura_ayacucho.data.model.MovieResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    /**
     * Se realiza una petición GET a la API de The Movie DB para obtener las películas populares
     * */
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey : String, // Se pasa como parámetro el API Key
        @Query("region") region : String// Se pasa como parámetro la región
    ): MovieResult // Se retorna un objeto de tipo MovieResult

    //get por si ID :https://api.themoviedb.org/3/movie/{movie_id}?api_key=TU_API_KEY
    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId : Int, // Se pasa como parámetro el ID de la película
        @Query("api_key") apiKey : String, // Se pasa como parámetro el API Key
    ): MovieDetail
}

/**
 * Este objeto se encarga de crear el servicio de Retrofit
 * */
object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()) // Este es el convertidor que se encarga de convertir la respuesta en un objeto de tipo Gson
            .build()
            .create(RetrofitService::class.java)
    }
}