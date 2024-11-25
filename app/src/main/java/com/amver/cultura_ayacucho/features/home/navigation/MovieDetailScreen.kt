package com.amver.cultura_ayacucho.features.home.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.features.home.components.MovieDailCard
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeDataView

@Composable
fun MovieDetailScreen(movieId: Int, viewModel: HomeDataView = viewModel(), navController: NavController) {
    LaunchedEffect(movieId){ // Efecto de lanzamiento
        Log.e("MovieDetailScreen", "movieId: buscar $movieId") // Log para verificar el id de la película
        viewModel.getMovieById(movieId)// función para obtener la película por id
    }

    val movie by viewModel.selectedMovie.collectAsState() // se obtiene la película por id

    if (movie != null) {
        MovieDailCard(movie = movie!!, navController = navController)
    } else {
        Log.e("MovieDetailScreen", "movieId: $movieId")
        Text("Cargando...")
    }
}