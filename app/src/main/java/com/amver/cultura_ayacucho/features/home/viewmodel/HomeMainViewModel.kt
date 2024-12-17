package com.amver.cultura_ayacucho.features.home.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.place.Place
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.data.repository.PlaceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeMainViewModel:ViewModel() {
    private val placeRepository = PlaceRepository()

    //Popular places
    private val _popularPlaces = mutableStateListOf<PlacesItem>()
    val popularPlaces: SnapshotStateList<PlacesItem> = _popularPlaces

    //El estado de la UI
    private val _uiState = MutableStateFlow<ApiState<List<PlacesItem>>>(ApiState.Loading)
    val uiState: StateFlow<ApiState<List<PlacesItem>>> = _uiState
    //El lugar seleccionado
    private val _selectedPlaceState = MutableStateFlow<Place?>(null)
    val selectedPlaceState: StateFlow<Place?> = _selectedPlaceState

    //La categoría seleccionada
    private val _selectedCategory = mutableStateOf<String?>(null)
    val selectedCategory: State<String?> = _selectedCategory

    private var searchJob: Job? = null
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        fetchPlacesPopular()
    }
    //Obtener lugares populares
    private fun fetchPlacesPopular(){
        viewModelScope.launch {
            _uiState.value = ApiState.Loading
            try {
                when (val result = placeRepository.getPopularPlaces()){
                    is ApiState.Success ->{
                        _popularPlaces.clear()
                        _popularPlaces.addAll(result.data)
                        _uiState.value = result
                    Log.d("HomeMainViewModel", "Popular places fetched ${result.data}")

                    }
                    is ApiState.Error ->{
                        _uiState.value = result
                        Log.e("HomeMainViewModel", "Error fetching popular places: ${result.message}")
                    }
                    else -> {
                        Log.e("HomeMainViewModel", "Error fetching popular places: Unknown error")
                    }
                }
            }catch (e: Exception){
                _uiState.value = ApiState.Error("Error inesperado: ${e.message}")
                Log.e("HomeMainViewModel", "Error fetching popular places: ${e.message}")
            }
        }
    }

    //Obtener lugar por id
    fun getPlaceById(placeId:Int){
        viewModelScope.launch {
            try {
                when (val result= placeRepository.getPlaceById(placeId) ){
                    is ApiState.Success->{
                        _selectedPlaceState.value = result.data
                        Log.d("HomeMainViewModel", "Place fetched by id: ${result.data}")
                    }
                    is ApiState.Error->{
                        Log.e("HomeMainViewModel", "Error fetching place by id: ${result.message}")
                    }
                    else -> {
                        Log.e("HomeMainViewModel", "Error fetching place by id: Unknown error")
                    }
                }
            }catch (e: Exception){
                Log.e("HomeMainViewModel", "Error fetching place by id: ${e.message}")
            }
        }
    }

    fun retryFetchPlacesPopular(){
        fetchPlacesPopular()
    }

    //Obtener lugares por categoría
    fun getPlacesByCategory(category: String){
        _selectedCategory.value = category
        when(category){
            "Popular" -> fetchPlacesPopular()
            else -> {
                viewModelScope.launch {
                    try {
                        when (val result = placeRepository.getPlacesByCategory(category)){
                            is ApiState.Success ->{
                                _popularPlaces.clear()
                                _popularPlaces.addAll(result.data)
                                _uiState.value = result
                                Log.d("HomeMainViewModel", "Places fetched by category: ${result.data}")
                            }
                            is ApiState.Error ->{
                                _uiState.value = result
                                Log.e("HomeMainViewModel", "Error fetching places by category: ${result.message}")
                            }
                            else -> {
                                Log.e("HomeMainViewModel", "Error fetching places by category: Unknown error")
                            }
                        }
                    }catch (e: Exception){
                        _uiState.value = ApiState.Error("Error inesperado: ${e.message}")
                        Log.e("HomeMainViewModel", "Error fetching places by category: ${e.message}")
                    }
                }
            }
        }
    }

    //Buscar lugares
    fun searchPlaces(query: String){
        _searchQuery.value = query
        searchJob?.cancel()

        //Si la consulta está vacía, obtener lugares populares
        if(query.isEmpty()){
            fetchPlacesPopular()
            return
        }

        searchJob = viewModelScope.launch {
            // Añade un pequeño delay para evitar muchas llamadas mientras el usuario escribe
            delay(300)
            try {
                _uiState.value = ApiState.Loading
                when(val result = placeRepository.searchPlaces(query)){
                    is ApiState.Success->{
                        _popularPlaces.clear()
                        _popularPlaces.addAll(result.data)
                        _uiState.value = result
                        Log.d("HomeMainViewModel", "Places fetched by search: ${result.data}")
                    }
                    is ApiState.Error->{
                        _uiState.value = result
                        Log.e("HomeMainViewModel", "Error fetching places by search: ${result.message}")
                    }
                    else -> {
                        Log.e("HomeMainViewModel", "Error fetching places by search: Unknown error")
                    }
                }
            }catch (e: Exception){
                _uiState.value = ApiState.Error("Error inesperado: ${e.message}")
                Log.e("HomeMainViewModel", "Error fetching places by search: ${e.message}")
            }
        }
    }

}
