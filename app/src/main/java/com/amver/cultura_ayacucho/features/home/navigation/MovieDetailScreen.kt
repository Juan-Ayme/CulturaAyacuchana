package com.amver.cultura_ayacucho.features.home.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amver.cultura_ayacucho.features.home.components.MovieDailCard
import com.amver.cultura_ayacucho.features.home.data.HomeDataView

@Composable
fun movieDetailScreen(movieId: Int, viewModel: HomeDataView = viewModel()) {
    LaunchedEffect(movieId){
        Log.e("MovieDetailScreen", "movieId: buscar $movieId")
        viewModel.getMovieById(movieId)
    }

    val movie by viewModel.selectedMovie.collectAsState()

    if (movie != null) {
        MovieDailCard(movie = movie!!)
    } else {
        Log.e("MovieDetailScreen", "movieId: $movieId")
        Text("Cargando...")
    }
}