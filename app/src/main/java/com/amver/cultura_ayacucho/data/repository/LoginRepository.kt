package com.amver.cultura_ayacucho.data.repository

import android.util.Log
import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.login.LoginError
import com.amver.cultura_ayacucho.data.model.login.LoginRequestUser
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class LoginRepository( private val apiLogin: ApiLogin = RetrofitServiceFactoryMain.createService(ApiLogin::class.java)) {

    suspend fun loginUser(username: String,password: String):ApiState<LoginResponseUser>{
        return try {
            val loginRequest = LoginRequestUser(
                username = username,
                password = password
            )
            val response = apiLogin.loginUserApi(loginRequest)

            Log.d("LoginViewModel", "User logged in successfully: $response")
            ApiState.Success(response)
        }catch (e:HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginError::class.java)
            Log.e("LoginViewModel", "Login failed: ${errorResponse.message}")
            ApiState.Error(errorResponse.message)
        }catch (e:IOException){
            Log.e("LoginViewModel", "Login failed: ${e.message}")
            ApiState.Error(e.message.toString())
    }catch (e:Exception){
            Log.e("LoginViewModel", "Login failed: ${e.message}")
            ApiState.Error(e.message.toString())
        }
    }
}