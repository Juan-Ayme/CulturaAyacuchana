package com.amver.cultura_ayacucho.features.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryUserEx
import com.amver.cultura_ayacucho.data.model.register.RegisterRequestUser
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegistrationViewModel: ViewModel(){
    //Se crea una instancia de la interfaz ApiLogin
    private val apiLogin: ApiLogin = RetrofitServiceFactoryUserEx.makeRetrofitService()

    // Estado de la respuesta del registro, inicializado con un valor nulo
    private val _registrationState = MutableStateFlow<Result<RegisterResponseUser>?>(null) //mutableStateOf es un objeto que se puede cambiar
    val registrationState: StateFlow<Result<RegisterResponseUser>?> = _registrationState //state es un objeto que se puede observar

    // Variable para almacenar el resultado del registro (opcional)
    private val _registerResult = MutableStateFlow<RegisterResponseUser?>(null)
    val registerResult: StateFlow<RegisterResponseUser?> = _registerResult

    // Función para registrar un usuario en la API
    fun registerUser(email: String, username: String, password: String, fullName: String) {
        // Lanza una tarea en el contexto de la vista (viewModelScope)
        viewModelScope.launch {
            try {
                // Crear el objeto de solicitud de registro con los datos proporcionados
                val response = apiLogin.registrationUser(
                    RegisterRequestUser(
                        email = email,
                        username = username,
                        password = password,
                        fullName = fullName
                    )
                )
                // Realiza la llamada al API para registrar al usuario

                _registrationState.value = Result.success(response)
                Log.d("RegistrationViewModel", "User registered successfully: $response")
                _registerResult.value = response
            } catch (e: Exception) {

                _registrationState.value = Result.failure(e)
                Log.e("RegistrationViewModel", "Registration failed: ${e.message}")
            }
        }
    }
}