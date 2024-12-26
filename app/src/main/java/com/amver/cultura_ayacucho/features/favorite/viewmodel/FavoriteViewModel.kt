package com.amver.cultura_ayacucho.features.favorite.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    //Instancia de FavoriteRepository
    private val placeRepositoryFavorite = FavoriteRepository()

    //SharedPreferences para guardar el token y el username
    private val sharedPref: SharedPreferences = getApplication<Application>()
        .getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val username = sharedPref.getString("username", null)
    private val token = sharedPref.getString("token", null)

    //Lista mutable de lugares favoritos
    private val _favoritePlaces = mutableStateListOf<PlacesItem>()
    val favoritePlaces: SnapshotStateList<PlacesItem> = _favoritePlaces

    //MutableStateFlow para manejar el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    //MutableStateFlow para manejar el estado de error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    //MutableStateFlow para manejar el estado de favorito
    private val _favoriteState = MutableStateFlow<ApiState<Boolean>?>(null)
    val favoriteState: StateFlow<ApiState<Boolean>?> = _favoriteState

    fun getFavoritePlaces(){
        viewModelScope.launch {
            try {
                _error.value = null

                when(val result = placeRepositoryFavorite.getFavoritePlaces(username!!,token!!)){
                    is ApiState.Success->{
                        _favoritePlaces.clear()
                        _favoritePlaces.addAll(result.data)
                        _isLoading.value = true
                        Log.d("FavoriteViewModel", "Favorite places fetched: ${result.data}")
                    }
                    is ApiState.Error->{
                        Log.e("FavoriteViewModel", "Error fetching favorite places: ${result.message}")
                    }
                    else ->{
                        Log.e("FavoriteViewModel", "Error fetching favorite places: $result")
                    }
                }
            }catch (e:Exception){
                _error.value = "Error inesperado: ${e.message}"
                Log.e("FavoriteViewModel", "Error fetching favorite places: ${e.message}")
            }
        }
    }

    fun addFavoritePlace(placeId:Int){
        viewModelScope.launch {
            try {
                _error.value = null
                _isLoading.value = true

                when(val result = placeRepositoryFavorite.addFavoritePlace(username!!,placeId,token!!)){
                    is ApiState.Success->{
                        _favoriteState.value = ApiState.Success(true)
                        Log.d("FavoriteViewModel", "Place added to favorites")
                    }
                    is ApiState.Error->{
                        _favoriteState.value = ApiState.Error(result.message)
                        Log.e("FavoriteViewModel", "Error adding place to favorites: ${result.message}")
                    }
                    else ->{
                        _favoriteState.value = ApiState.Error("Error desconocido")
                        Log.e("FavoriteViewModel", "Error adding place to favorites: Error desconocido")
                    }
                }
            }catch (e:Exception){
                _error.value = "Error inesperado: ${e.message}"
                Log.e("FavoriteViewModel", "Error adding place to favorites: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteFavoritePlace(placeId:Int){
        viewModelScope.launch {
            try {
                _error.value = null
                _isLoading.value = true

                when(val result = placeRepositoryFavorite.deleteFavoritePlace(username!!,placeId,token!!)){

                    is ApiState.Success->{
                        _favoriteState.value = ApiState.Success(true)
                        Log.d("FavoriteViewModel", "El lugar se eliminó de favoritos")
                    }
                    is ApiState.Error->{
                        _favoriteState.value = ApiState.Error(result.message)
                        _error.value = result.message
                        Log.e("FavoriteViewModel", "Error al eliminar el lugar favorito: ${result.message}")
                    }
                    else ->{
                        _favoriteState.value = ApiState.Error("Error desconocido")
                        Log.e("FavoriteViewModel", "Error deleting place from favorites: Error desconocido")
                    }
                }
            }catch (e:Exception){
                _error.value = "Error inesperado: ${e.message}"
                Log.e("FavoriteViewModel", "Error deleting place from favorites: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }
}