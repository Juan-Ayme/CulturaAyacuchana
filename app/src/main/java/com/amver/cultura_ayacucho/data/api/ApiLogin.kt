package com.amver.cultura_ayacucho.data.api

import com.amver.cultura_ayacucho.data.model.login.LoginRequestUser
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import com.amver.cultura_ayacucho.data.model.register.RegisterRequestUser
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

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
