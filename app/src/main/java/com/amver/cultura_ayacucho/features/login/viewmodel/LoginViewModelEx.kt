package com.amver.cultura_ayacucho.features.login.viewmodel

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import com.amver.cultura_ayacucho.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModelEx(application: Application):AndroidViewModel(application) {

     private val loginRepository = LoginRepository()

    private val _loginState = mutableStateOf<Boolean?>(false)
    val loginState: MutableState<Boolean?> = _loginState

    private val _uiState = MutableStateFlow<ApiState<LoginResponseUser>>(ApiState.Empty)
    val uiState: StateFlow<ApiState<LoginResponseUser>> = _uiState

    fun loginUserEX(username:String, password:String, navController: NavController){
        viewModelScope.launch {
            try {
                when(val result = loginRepository.loginUser(username,password)){
                    is ApiState.Success -> {
                        _uiState.value = ApiState.Loading
                        _loginState.value = true
                        saveTokenToPreferences(result.data.token,result.data.username,result.data.success)
                        scheduleClearTokenFromPreferences(navController)
                        navController.navigate(ScreenNavigation.User.route)
                        Log.d("LoginViewModelEX", "User logged in successfully: $result")
                    }
                    is ApiState.Error -> {
                        _loginState.value = false
                        _uiState.value = ApiState.Error(result.message)
                        Log.e("LoginViewModelEX", "Login failed: ${result.message}")
                    }
                    else -> {
                        _loginState.value = false
                        Log.e("LoginViewModelEX", "Login failed: $result")
                    }
                }
            }catch (e:Exception){
                _uiState.value = ApiState.Error(e.message.toString())
                Log.e("LoginViewModelEX", "Login failed: ${e.message}")
            }
        }
    }

    private fun saveTokenToPreferences(token: String, username: String, success: Boolean) {
        val sharedPrefences = getApplication<Application>().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)

        val editor = sharedPrefences.edit()
        editor.putString("token",token)
        editor.putString("username",username)
        editor.putBoolean("success",success)
        editor.apply()
    }

    fun getUsernameFromPreferences():String? {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
        return sharedPreferences.getString("username",null)
    }

    fun isSuccessFullLogin():Boolean {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("success",false)
    }

    fun clearTokenFromPreferences(navController: NavController){
        val sharedPreferences = getApplication<Application>().getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.remove("success")
        editor.remove("username")
        editor.apply()
        navController.navigate(ScreenNavigation.Login.route)
    }

    //Funcion que se encarga de programar la limpieza del token de las preferencias compartidas
    private fun scheduleClearTokenFromPreferences(navController: NavController){
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable{
            clearTokenFromPreferences(navController)
        }
        handler.postDelayed(runnable,2*60*1000)//Se limpia el token despues de 30 minutos
    }
}