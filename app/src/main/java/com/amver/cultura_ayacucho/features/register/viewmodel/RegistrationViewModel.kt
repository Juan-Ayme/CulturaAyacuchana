package com.amver.cultura_ayacucho.features.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.login.LoginError
import com.amver.cultura_ayacucho.data.model.register.RegisterRequestUser
import com.amver.cultura_ayacucho.data.model.register.RegisterResponseUser
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException


class  RegistrationViewModel: ViewModel(){
    //Se crea una instancia de la interfaz ApiLogin
    //private val apiLogin: ApiLogin = RetrofitServiceFactoryUserEx.makeRetrofitService()
    private val apiRegister: ApiLogin = RetrofitServiceFactoryMain.createService(ApiLogin::class.java)

    // Estado de la respuesta del registro, inicializado con un valor nulo
    private val _registrationState = MutableStateFlow<Result<RegisterResponseUser>?>(null) //mutableStateOf es un objeto que se puede cambiar
    private val registrationState: StateFlow<Result<RegisterResponseUser>?> = _registrationState //state es un objeto que se puede observar

    // Variable para almacenar el resultado del registro (opcional)
    private val _registerResult = MutableStateFlow<RegisterResponseUser?>(null)
    val registerResult: StateFlow<RegisterResponseUser?> = _registerResult

    private val _errorState = MutableStateFlow<LoginError?>(null)
        val errorState: StateFlow<LoginError?> = _errorState

    // Función para registrar un usuario en la API
    fun registerUser(email: String, username: String, password: String, fullName: String,navController: NavController) {
        // Lanza una tarea en el contexto de la vista (viewModelScope)
        viewModelScope.launch {
            try {
                // Crear el objeto de solicitud de registro con los datos proporcionados
                val response = apiRegister.registrationUser(
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
                //Limpiar el mensaje de error previo
                _errorState.value = null

                if(registrationState.value?.isSuccess == true){
                    //Si el registro es exitoso, navegamos a la pantalla de registro exitoso
                    navController.navigate(ScreenNavigation.SuccessfulRegistration.route)
                }

            } catch (e: Exception) {
                _registrationState.value = Result.failure(e)
                Log.e("RegistrationViewModel", "Registration failed: ${e.message}")

                //Manejamos específicamente el error de la API

                when(e){
                    is HttpException ->{
                        try {
                            //Convertimos el error response body a nuestro modelo de error
                            val errorBody = e.response()?.errorBody()?.string()
                            val apiError = Gson().fromJson(errorBody, LoginError::class.java)
                            _errorState.value = apiError
                        }catch (e: Exception){
                            //Si hay un error al pasear, creamos un api genrico
                            _errorState.value = LoginError(
                                path = "",
                                message = "El formato del email no es valido",
                                statusCode = 400,
                                timestamp = "",
                                errors = listOf(e.message ?: "El formato del email no es valido")
                            )
                            Log.e("RegistrationViewModel", "Error parsing error body: ${e.message}"
                            )
                        }
                    }
                    else ->{
                        // Para otros tipos de errores, creamos un error genérico
                        _errorState.value = LoginError(
                            path = "",
                            message = "Error desconocido",
                            statusCode = 0,
                            timestamp = "",
                            errors = listOf(e.message ?: "Error desconocido")
                        )
                    }
                }
            }
        }
    }
}