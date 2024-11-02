package com.amver.cultura_ayacucho.features.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.model.MovieModel
import com.amver.cultura_ayacucho.features.home.viewmodel.topBarView
import com.amver.cultura_ayacucho.features.home.data.HomeDataView


/**
 *  El homeScreen es el componente principal de la pantalla principal
 * */

@Composable
fun homeMainScreen(navController: NavController,viewModel: HomeDataView = viewModel()) {

    val movies = viewModel.moviesPopular.toList()

    Scaffold(topBar = { topBarView() }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = "Home Screen Lista de lugares turísticos")
            Text(text = "Número de lugares turísticos: ${movies.size}")
            LazyColumn {
                items(movies) { movie ->
                    MovieItem(movie,navController)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieModel, navController: NavController) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(ScreenNavigation.MovieDetail.createRoute(movie.id))
            } // Ejecuta la función onMovieClick al hacer clic
    ) {
        Card(modifier = Modifier.padding(8.dp)) {
            Text(text = movie.title)
            Text(text = movie.overview)
            AsyncImage(model = imageUrl, contentDescription = movie.title)
        }
    }
}