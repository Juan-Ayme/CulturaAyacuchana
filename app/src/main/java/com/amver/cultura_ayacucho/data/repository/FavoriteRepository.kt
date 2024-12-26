package com.amver.cultura_ayacucho.data.repository

import android.util.Log
import com.amver.cultura_ayacucho.data.api.ApiFavorite
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.favorite.CreatePlaceFavoriteResponse
import com.amver.cultura_ayacucho.data.model.favorite.DeletePlaceFavoriteResponse
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import retrofit2.HttpException
import java.io.IOException

class FavoriteRepository {
    private val retrofitService: ApiFavorite = RetrofitServiceFactoryMain.createService(ApiFavorite::class.java)

    //Obtener los lugares favoritos
    suspend fun getFavoritePlaces(username:String,token:String):ApiState<List<PlacesItem>>{
        return try {
            val response = retrofitService.getFavoritePlaces(username, "Bearer $token")
            ApiState.Success(response)

        }catch (e: Exception){
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> "Error del servidor: ${e.code()}"
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }

    //Agregar un lugar a favoritos
    suspend fun addFavoritePlace(username:String,placeId:Int,token:String):ApiState<CreatePlaceFavoriteResponse>{
        return try {
            val response = retrofitService.addFavoritePlace(username=username, placeId =  placeId,bearerToken= "Bearer $token")
            ApiState.Success(response)

        }catch (e: Exception){
            Log.e("FavoriteRepository", "Token: $token - Username: $username - PlaceId: $placeId")
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> "Error del servidor: ${e.code()}"
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }

    //Eliminar un lugar de favoritos
    suspend fun deleteFavoritePlace(username:String,placeId:Int,token:String):ApiState<DeletePlaceFavoriteResponse>{
        return try {
            val response = retrofitService.deleteFavoritePlace(username=username, placeId =  placeId,bearerToken = "Bearer $token")
            Log.d("FavoriteRepository", "Token: $token - Username: $username - PlaceId: $placeId")
            Log.e("FavoriteRepository", "Response: ${response.message}")
            ApiState.Success(response)
        }catch (e: Exception){
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> {
                    when(e.code()){
                        400 -> "Error del Servidor: 400. Solicitud incorrecta"
                        401 -> "Credenciales incorrectas. Por favor vertifica tu usuario y contraseña"
                        403 -> "Error del servidor: 403. Acceso denegado"
                        else -> "Err6or del servidor: ${e.code()}"
                    }
                }
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }
}