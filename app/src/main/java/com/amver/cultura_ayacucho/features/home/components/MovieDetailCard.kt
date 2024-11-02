package com.amver.cultura_ayacucho.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.data.model.MovieDetail

@Composable
fun MovieDailCard(movie: MovieDetail) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        LazyColumn (modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ){
            item {
                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                AsyncImage(
                    model = imageUrl,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = movie.title,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
                Text(
                    text = "Fecha de lanzamiento: ${movie.release_date}",
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Descripción:",
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.overview,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Idioma: ${movie.original_language}")
                    Text(text = "Popularidad: ${movie.popularity}")
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Calificación: ${movie.vote_average} / 10")
                    Text(text = "Votos: ${movie.vote_count}")
                }
            }
        }
    }
}