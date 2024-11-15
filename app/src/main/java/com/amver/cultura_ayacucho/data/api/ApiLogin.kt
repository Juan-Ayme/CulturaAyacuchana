package com.amver.cultura_ayacucho.data.api

import com.amver.cultura_ayacucho.data.model.login.LoginRequestUser
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import com.amver.cultura_ayacucho.data.model.register.RegisterRequestUser
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiLogin {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun registrationUser(
        @Body registerRequest: RegisterRequestUser
    ): RegisterResponseUser

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginUserApi(
        @Body loginRequestUser: LoginRequestUser
    ): LoginResponseUser
}

object RetrofitServiceFactoryUserEx{
    fun makeRetrofitService(): ApiLogin{
        return Retrofit.Builder()
            .baseUrl("http://192.168.18.30:8080/api/v1/")
            // Se añade el convertidor GsonConverterFactory para convertir la respuesta en un objeto de tipo Gson
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiLogin::class.java)
    }
}