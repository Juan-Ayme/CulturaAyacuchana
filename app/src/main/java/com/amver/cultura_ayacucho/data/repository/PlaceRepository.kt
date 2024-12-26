package com.amver.cultura_ayacucho.data.repository

import android.util.Log
import com.amver.cultura_ayacucho.data.api.ApiPlace
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactoryMain
import com.amver.cultura_ayacucho.data.model.place.Place
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import okio.IOException
import retrofit2.HttpException

class PlaceRepository{
    private val retrofitService: ApiPlace = RetrofitServiceFactoryMain.createService(ApiPlace::class.java)
    private val favoriteRepository = FavoriteRepository()
    //Obtener los lugares populares
    suspend fun getPopularPlacesByFavorite(username: String?,token: String?): ApiState<List<PlacesItem>> {
        return try {
            //obtener lugares populares
            val popularPlacesResult = retrofitService.getPlacesPopular()

            //si no hay usuario logueado, devolver los lugares sin marcar favoritos
            if (username == null || token == null){
                return ApiState.Success(popularPlacesResult)
            }

            val favoriteRepositoryResult = favoriteRepository.getFavoritePlaces(username,token)

            //si no se puede obtener los lugares favoritos, devolver los lugares sin marcar favoritos
            if (favoriteRepositoryResult !is ApiState.Success){
                return ApiState.Success(popularPlacesResult)
            }

            //Crear un conjunto de IDs de favoritos para busqueda mas eficinete
            val favoriteIds = favoriteRepositoryResult.data.map { it.placeId }.toSet()

            //Marcar los lugares favoritos
            val popularPLacesWithFavorite = popularPlacesResult.map { placesItem ->
                placesItem.copyWithFavorite(isFavorite = placesItem.placeId in favoriteIds)
            }
            ApiState.Success(popularPLacesWithFavorite)

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
    suspend fun getPlacesByCategory(category: String,username: String?,token: String?): ApiState<List<PlacesItem>>{
        return try{
            val response = retrofitService.getPlacesByCategory(category)

            if (username == null || token == null){
                return ApiState.Success(response)
            }

            val favoriteRepositoryResult = favoriteRepository.getFavoritePlaces(username,token)

            //si no se puede obtener los lugares favoritos, devolver los lugares sin marcar favoritos
            if(favoriteRepositoryResult !is ApiState.Success){
                return ApiState.Success(response)
            }

            val favoriteIds = favoriteRepositoryResult.data.map { it.placeId }.toSet()

            val searchFavorite = response.map { placesItem ->
                placesItem.copyWithFavorite(isFavorite = placesItem.placeId in favoriteIds)
            }
            ApiState.Success(searchFavorite)
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
    suspend fun searchPlaces(keyword:String,username: String?,token: String?):ApiState<List<PlacesItem>>{
        return try {

            val searchPlaceResult = retrofitService.searchPlaces(keyword)
            if (username == null || token == null){
                return ApiState.Success(searchPlaceResult)
            }

            val favoriteRepositoryResult = favoriteRepository.getFavoritePlaces(username,token)
            //si no se puede obtener los lugares favoritos, devolver los lugares sin marcar favoritos
            if (favoriteRepositoryResult !is ApiState.Success){
                return ApiState.Success(searchPlaceResult)
            }

            val favoriteIds = favoriteRepositoryResult.data.map { it.placeId }.toSet()

            val searchPlaceWithFavorite = searchPlaceResult.map { placesItem ->
                placesItem.copyWithFavorite(isFavorite = placesItem.placeId in favoriteIds)
            }


            ApiState.Success(searchPlaceWithFavorite)
        }catch (e: Exception){
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> "Error del servidor: ${e.code()}"
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }


    //Obtener los lugares por categoria y provincia
    suspend fun getPlacesByCategoryAndProvince(category: String,province: String,username: String?,token: String?): ApiState<List<PlacesItem>>{
        return try{
            Log.e("PlaceRepository", "category: $category, province: $province")
            val response = retrofitService.getPlacesByCategoryAndProvince(category,province)
            if (username == null || token == null){
                return ApiState.Success(response)
            }

            val favoriteRepositoryResult = favoriteRepository.getFavoritePlaces(username,token)

            //si no se puede obtener los lugares favoritos, devolver los lugares sin marcar favoritos
            if(favoriteRepositoryResult !is ApiState.Success){
                return ApiState.Success(response)
            }

            val favoriteIds = favoriteRepositoryResult.data.map { it.placeId }.toSet()

            val searchFavorite = response.map { placesItem ->
                placesItem.copyWithFavorite(isFavorite = placesItem.placeId in favoriteIds)
            }

            Log.d("PlaceRepository", "getPlacesByCategoryAndProvince: $searchFavorite")
            ApiState.Success(searchFavorite)
        }catch (e: Exception){
            Log.e("PlaceRepository", "Error getPlacesByCategoryAndProvince: ${e.message}")
            val errorMessage = when(e){
                is IOException ->"Error de red. Verifica tu conexión a internet"
                is HttpException -> "Error del servidor: ${e.code()}"
                else -> "Error desconocido: ${e.message}"
            }
            ApiState.Error(errorMessage)
        }
    }

    //Obtener los lugares por provincia
    suspend fun getPlacesByProvince(province: String,username: String?,token: String?): ApiState<List<PlacesItem>>{
        return try{
            val response = retrofitService.getPlacesByProvince(province)
            if (username == null || token == null){
                return ApiState.Success(response)
            }

            val favoriteRepositoryResult = favoriteRepository.getFavoritePlaces(username,token)

            //si no se puede obtener los lugares favoritos, devolver los lugares sin marcar favoritos
            if(favoriteRepositoryResult !is ApiState.Success){
                return ApiState.Success(response)
            }

            val favoriteIds = favoriteRepositoryResult.data.map { it.placeId }.toSet()

            val searchFavorite = response.map { placesItem ->
                placesItem.copyWithFavorite(isFavorite = placesItem.placeId in favoriteIds)
            }
            ApiState.Success(searchFavorite)
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