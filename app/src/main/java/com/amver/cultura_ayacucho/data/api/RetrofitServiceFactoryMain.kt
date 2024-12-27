package com.amver.cultura_ayacucho.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitServiceFactoryMain {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS) // Aumenta el tiempo de espera de conexión
            .readTimeout(40, TimeUnit.SECONDS) // Aumenta el tiempo de espera de lectura
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.104:8080/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Este es el convertidor que se encarga de convertir la respuesta en un objeto de tipo Gson
            .build()
    }

    fun <T> createService(serviceClass: Class<T>):T{
        return retrofit.create(serviceClass)
    }
}