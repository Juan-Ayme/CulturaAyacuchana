package com.amver.cultura_ayacucho.features.home.screen.placesCard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.model.place.PlacesItem


@Composable
fun PopularPlaceCardComponent(placesItem: PlacesItem, navController: NavController) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(250.dp) // Mayor altura para una mejor visibilidad de la imagen
            .clickable {
                navController.navigate(ScreenNavigation.PlaceDetail.createRoute(placesItem.placeId))
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp) // Mayor elevación para una mejor profundidad
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de fondo
            AsyncImage(
                model = placesItem.urlImage,
                contentDescription = placesItem.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Degradado más oscuro para un mejor contraste del texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f) // Aumento de la opacidad
                            ),
                            startY = 180f // La pendiente comienza más abajo
                        )
                    )
            )

            // Content layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Titulo y localización
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(0.7f) // Limite el ancho para evitar la superposición con la clasificación
                ) {
                    Text(
                        text = placesItem.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = placesItem.location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }

                // Boton de clasificacion
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700), // Golden color
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(

                        text = placesItem.punctuationAverage.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Favorite button
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = Color(0xFF00BFA6).copy(alpha = 0.2f), // Aumento de la opacidad
                        shape = RoundedCornerShape(16.dp)
                    )
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = Color.White
                )
            }
        }
    }
}
