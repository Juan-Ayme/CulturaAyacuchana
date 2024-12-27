package com.amver.cultura_ayacucho.features.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import com.amver.cultura_ayacucho.data.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationMainViewModel:ViewModel() {

    private val registerRepository = RegisterRepository()

    private val _registrationState = MutableStateFlow<Boolean?>(null)
    val registrationState = _registrationState

    private val _uiState = MutableStateFlow<ApiState<RegisterResponseUser>>(ApiState.Empty)
    val uiState: StateFlow<ApiState<RegisterResponseUser>> = _uiState

    fun registerUser(email:String,username:String,password:String,fullName:String,navController: NavController){
        _uiState.value = ApiState.Loading
        viewModelScope.launch {
            try {
                when(val result = registerRepository.registerUser(email,username,password,fullName,navController = navController)){
                    is ApiState.Success -> {
                        _uiState.value = ApiState.Loading
                        _registrationState.value = true
                        _uiState.value = ApiState.Success(result.data)
                        navController.navigate(ScreenNavigation.SuccessfulRegistration.route)
                        Log.d("RegistrationViewModel", "User registered successfully: $result")
                    }
                    is ApiState.Error -> {
                        _registrationState.value = false
                        Log.e("RegistrationViewModel", "Registration failed: ${result.message}")
                        _uiState.value = ApiState.Error(result.message)
                    }
                    else -> {
                        _registrationState.value = false
                    }
                }
            }catch (e:Exception){
                Log.e("RegistrationViewModel", "Registration failed: ${e.message}")
                _uiState.value = ApiState.Error(e.message.toString())
            }
        }
    }


}