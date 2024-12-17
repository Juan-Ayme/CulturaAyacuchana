package com.amver.cultura_ayacucho.data.api

//Esta clase es un sealed class que se encarga de manejar el estado de la API
sealed class ApiState<out T>{
    //Este objeto maneja el estado de la API cuando se carga la api
    data object Loading: ApiState<Nothing>()
    //Este objeto maneja el estado de la API cuando se produce un error
    data class Success<T>(val data: T): ApiState<T>()
    //Este objeto maneja el estado de la API cuando se produce un error
    data class Error(val message:String ): ApiState<Nothing>()

}