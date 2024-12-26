package com.amver.cultura_ayacucho.features.home.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
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

class HomeMainViewModel(application: Application): AndroidViewModel(application) {
    private val placeRepository = PlaceRepository()

    //Popular places
    private val _popularPlaces = mutableStateListOf<PlacesItem>()
    val popularPlaces: SnapshotStateList<PlacesItem> = _popularPlaces

    private val _filterdPlaces = MutableStateFlow<List<PlacesItem>>(emptyList())
    val filterdPLaces: StateFlow<List<PlacesItem>> = _filterdPlaces

    //El estado de la UI
    private val _uiState = MutableStateFlow<ApiState<List<PlacesItem>>>(ApiState.Loading)
    val uiState: StateFlow<ApiState<List<PlacesItem>>> = _uiState
    //El lugar seleccionado
    private val _selectedPlaceState = MutableStateFlow<Place?>(null)
    val selectedPlaceState: StateFlow<Place?> = _selectedPlaceState

    //La categoría seleccionada
    private val _selectedCategory = mutableStateOf<String?>(null)
    val selectedCategory: State<String?> = _selectedCategory

    //para guardar las busquedasy que trabaje en segundo plano
    private var searchJob: Job? = null
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    //Buscar las preferencias del usuario
    private val sharedPref: SharedPreferences = getApplication<Application>()
        .getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val username = sharedPref.getString("username", null)
    private val token = sharedPref.getString("token", null)

    //place por categoria y provincia
    private val _placesByCategoryAndProvince = mutableStateListOf<PlacesItem>()
    val placesByCategoryAndProvince: SnapshotStateList<PlacesItem> = _placesByCategoryAndProvince

    //Provincia por defecto
    val selectedProvince = mutableStateOf("Ayacucho Norte")

    private val _placesByProvince = mutableStateListOf<PlacesItem>()
    val placesByProvince: SnapshotStateList<PlacesItem> = _placesByProvince

    init {
        fetchPlacesPopular()
        setProvince("Ayacucho Norte")
    }

    //Obtener lugares populares
    private fun fetchPlacesPopular(){
        viewModelScope.launch {
            _uiState.value = ApiState.Loading
            try {
                when (val result = placeRepository.getPopularPlacesByFavorite(username, token)){
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
                        when (val result = placeRepository.getPlacesByCategory(category,username,token)){
                            is ApiState.Success ->{
                                //_popularPlaces.clear()
                                //_popularPlaces.addAll(result.data) //Añadir lugares por categoría
                                _filterdPlaces.value = emptyList()
                                _filterdPlaces.value = result.data
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
                when(val result = placeRepository.searchPlaces(query,username,token)){
                    is ApiState.Success->{
                        _popularPlaces.clear()
                        _selectedCategory.value = null
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

    //Lugares por categoría y provincia

    //fun getPlacesByCategoryAndProvince(category: String, province: String)
    object Provinces{
        val name = listOf(
            "Ayacucho Norte",
            "Ayacucho Centro",
            "Ayacucho Sur",
            "Huamanga",
            "Vilcashuaman",
            "Parinacochas",
            "Quinua"
        )
    }
    private fun getPlacesByCategoryAndProvince(category: String, province: String){
        viewModelScope.launch {
            try {
                Log.e("HomeMainViewModel", "category: natural, province: Huamanga")
                when (val result = placeRepository.getPlacesByCategoryAndProvince(category, province = province,username = username,token = token)){
                    is ApiState.Success ->{
                        _placesByCategoryAndProvince.clear()
                        _placesByCategoryAndProvince.addAll(result.data)
                        _uiState.value = result

                        Log.d("HomeMainViewModel", "Places fetched by category and province: ${result.data}")
                    }
                    is ApiState.Error ->{
                        _uiState.value = result
                        Log.e("HomeMainViewModel", "Error fetching places by category and province: ${result.message}")
                    }
                    else -> {
                        Log.e("HomeMainViewModel", "Error fetching places by category and province: Unknown error")
                    }
                }
            }catch (e: Exception){
                _uiState.value = ApiState.Error("Error inesperado: ${e.message}")
                Log.e("HomeMainViewModel", "Error fetching places by category and province: ${e.message}")
            }
        }
    }

    //Funcion para seleccionar la provincia
    fun setProvince(province: String){
        selectedProvince.value = province
        Log.e("HomeMainViewModel", "province: $province")
        if (selectedCategory.value != null)
        {
            getPlacesByCategoryAndProvince(selectedCategory.value.toString(),province)
            if (selectedCategory.value == "Popular")
            {
                getPlacesByProvince(province)
            }

        }else
        {
            getPlacesByProvince(province)
        }
    }

    //funcion para obtener lugares por provincia
    private fun getPlacesByProvince(province: String){
        viewModelScope.launch {
            try {
                when (val result = placeRepository.getPlacesByProvince(province,username,token)){
                    is ApiState.Success ->{
                        _placesByProvince.clear()
                        _placesByProvince.addAll(result.data)
                        _uiState.value = result

                        Log.d("HomeMainViewModel", "Places fetched by province: ${result.data}")
                    }
                    is ApiState.Error ->{
                        _uiState.value = result
                        Log.e("HomeMainViewModel", "Error fetching places by province: ${result.message}")
                    }
                    else -> {
                        Log.e("HomeMainViewModel", "Error fetching places by province: Unknown error")
                    }
                }
            }catch (e: Exception){
                _uiState.value = ApiState.Error("Error inesperado: ${e.message}")
                Log.e("HomeMainViewModel", "Error fetching places by province: ${e.message}")
            }
        }
    }

}
