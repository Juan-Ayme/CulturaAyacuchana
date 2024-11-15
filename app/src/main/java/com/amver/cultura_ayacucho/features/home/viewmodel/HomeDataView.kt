package com.amver.cultura_ayacucho.features.home.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amver.cultura_ayacucho.data.model.movie.MovieDetail
import com.amver.cultura_ayacucho.data.model.movie.MovieModel
import com.amver.cultura_ayacucho.data.api.RetrofitServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 *
 * En HomeDataView esta utilizando ViewModel para almacenar y manejar los datos de la pantalla,
 * consciente del ciclo de vida de la actividad o fragmento.
 *
 * */

class HomeDataView:ViewModel() {
    private val retrofitService = RetrofitServiceFactory.makeRetrofitService()

    private val _popularMovies = mutableStateListOf<MovieModel>() //mutableStateListOf es una lista que se puede observar
    val moviesPopular: SnapshotStateList<MovieModel> = _popularMovies //snapshotStateList es una lista que se puede observar

    private val _selectedMovie = MutableStateFlow<MovieDetail?>(null) //mutableStateOf es un objeto que se puede observar
    val selectedMovie: StateFlow<MovieDetail?> = _selectedMovie //state es un objeto que se puede observar


    init{
        fetchPopularMovies() //se llama a la función fetchPopularMovies
    }

    //fetchPopularMovies es una función que se encarga de obtener las películas populares
    private fun fetchPopularMovies(){
        viewModelScope.launch { //se lanza un hilo de trabajo
            try {
                val response = retrofitService.listPopularMovies(
                    "10a194a01f1ef814a37ded893c4c9221",
                    "US")
                _popularMovies.clear()
                _popularMovies.addAll(response.results) //se añaden las películas a la lista de películas populares
                Log.d("HomeDataView", "Popular movies fetched")
            }catch (e: Exception){
                println("Error fetching popular movies")
            }
        }
    }

    //getMovieById es una función que se encarga de obtener una película por su ID
    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie  = retrofitService.getMovieById(movieId,"10a194a01f1ef814a37ded893c4c9221")
                _selectedMovie.value = movie
                Log.e("HomeDataView", "Movie fetched by id")
            }catch (e: Exception){
                Log.e("HomeDataView", "Error fetching movie by id")
            }
        }
    }

}