package com.amver.cultura_ayacucho.data.repository

import com.amver.cultura_ayacucho.data.api.ApiPlace
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.place.Place
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import okio.IOException
import retrofit2.HttpException

class PlaceRepository{
    private val retrofitService: ApiPlace = RetrofitServiceFactoryMain.createService(ApiPlace::class.java)

    //Obtener los lugares populares
    suspend fun getPopularPlaces(): ApiState<List<PlacesItem>>{
        return try{
            val response = retrofitService.getPlacesPopular()
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

    //Obtener un lugar por su id
    suspend fun getPlaceById(placeId: Int): ApiState<Place>{
        return try{
            val response = retrofitService.getPlaceDetail(placeId)
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

    //Obtener los lugares por categoria
    suspend fun getPlacesByCategory(category: String): ApiState<List<PlacesItem>>{
        return try{
            val response = retrofitService.getPlacesByCategory(category)
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

    //Buscar lugares
    suspend fun searchPlaces(keyword:String):ApiState<List<PlacesItem>>{
        return try {
            val reponse = retrofitService.searchPlaces(keyword)
            ApiState.Success(reponse)
        }catch (e: Exception){
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> "Error del servidor: ${e.code()}"
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }

}