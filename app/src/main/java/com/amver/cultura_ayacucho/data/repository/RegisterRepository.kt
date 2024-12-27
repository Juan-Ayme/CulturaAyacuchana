package com.amver.cultura_ayacucho.data.repository

import android.util.Log
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.register.RegisterErrorUSer
import com.amver.cultura_ayacucho.data.model.register.RegisterRequestUser
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterRepository {
    private val repositoryService: ApiLogin = RetrofitServiceFactoryMain.createService(ApiLogin::class.java)

    suspend fun registerUser(email:String,username:String,password:String,fullName:String,navController: NavController): ApiState<RegisterResponseUser> {
        return try {
            val registerRequest = RegisterRequestUser(
                email = email,
                username = username,
                password = password,
                fullName = fullName
            )

            val response = repositoryService.registrationUser(registerRequest)
            Log.d("RegistrationViewModel", "User registered successfully: $response")
        ApiState.Success(response)
        }catch (e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterErrorUSer::class.java)
            Log.e("RegistrationViewModel", "Registration failed: ${errorResponse.message}")
            ApiState.Error(errorResponse.errors.firstOrNull().toString())
        }catch (e:Exception){
            Log.e("RegistrationViewModel", "Registration failed: ${e.message}")
            ApiState.Error(e.message.toString())
        }
    }
}