package com.amver.cultura_ayacucho.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.data.model.movie.MovieDetail

@Composable
fun MovieDailCard(movie: MovieDetail, navController : NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1B1F))
    ){
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentScale = ContentScale.Crop,
        )

        //Boton de regreso
        IconButton(
            onClick = {navController.popBackStack()},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp,40.dp, 0.dp, 0.dp)
                //trasnparante
                .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                .size(40.dp)

        ) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.icon_back),
                contentDescription = "Regresar",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }
        //Boton de favoritos
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(16.dp,40.dp, 0.dp, 0.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritos",
                tint = Color.White
            )
        }
// Contenido Principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            // Título
            Text(
                text = movie.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Ubicación
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Perú, Ayacucho",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Tabs de navegación
            var selectedTab by remember { mutableStateOf(0) }
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                ) {
                    Text(
                        text = "General",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                ) {
                    Text(
                        text = "Comentarios",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }

            // Información de Duración y Calificación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Duración
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Duración",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Duración: ${movie.runtime} min",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Calificación
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Calificación",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Calificación: ${movie.vote_average} / 10",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = "Votos: ${movie.vote_count}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Descripción con límite de líneas y botón "Ver más"
            var isExpanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                // Descripción del texto
                Text(
                    text = movie.overview,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Limita a 3 líneas cuando no está expandido
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp) // Espacio para el degradado y el botón
                )

                // Degradado en la parte inferior de la descripción
                if (!isExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.White)
                                )
                            )
                    )
                }

                // Botón de "Ver más" o "Ver menos" superpuesto
                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (isExpanded) "Ver menos" else "Ver más",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Contenido Principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            // Título
            Text(
                text = movie.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Ubicación
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Perú, Ayacucho",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Tabs de navegación
            var selectedTab by remember { mutableStateOf(0) }
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                ) {
                    Text(
                        text = "General",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                ) {
                    Text(
                        text = "Comentarios",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }

            // Información de Duración y Calificación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Duración
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Duración",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Duración: ${movie.runtime} min",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Calificación
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Calificación",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Calificación: ${movie.vote_average} / 10",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = "Votos: ${movie.vote_count}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Descripción con límite de líneas y botón "Ver más"
            var isExpanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                // Descripción del texto
                Text(
                    text = movie.overview,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Limita a 3 líneas cuando no está expandido
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp) // Espacio para el degradado y el botón
                )

                // Degradado en la parte inferior de la descripción
                if (!isExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.White.copy(alpha = 0f), Color.White),
                                    startY = 0f,
                                    endY = 100f
                                )
                            )
                    )
                }

                // Botón de "Ver más" o "Ver menos" superpuesto
                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (isExpanded) "Ver menos" else "Ver más",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }




//        //Contenido Principal
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
//                .background(Color.White)
//                .padding(24.dp)
//        ){
//            //Titulo
//            Text(
//                text = movie.title,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
//
//            //ubicacion
//            Row(
//                modifier = Modifier.padding(top = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Icon(
//                    imageVector = Icons.Default.LocationOn,
//                    contentDescription = "Ubicación",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(20.dp)
//                )
//                Text(
//                    text = "Perú, Ayacucho",
//                    fontSize = 14.sp,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(start = 4.dp)
//                )
//            }
//
//            //tabs de navegacion
//            var selectedTab by remember { mutableStateOf(0) }
//            TabRow(
//                selectedTabIndex = selectedTab,
//                modifier = Modifier.padding(vertical = 16.dp)
//            ) {
//                Tab(
//                    selected = selectedTab ==0,
//                    onClick = {selectedTab =0}
//                ) {
//                    Text(
//                        text = "General",
//                        modifier = Modifier.padding(vertical =8.dp),
//                        color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else Color.Gray
//                    )
//                }
//                Tab(
//                    selected = selectedTab ==1,
//                    onClick = {selectedTab =1}
//                ) {
//                    Text(
//                        text = "Comentarios",
//                        modifier = Modifier.padding(vertical =8.dp),
//                        color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else Color.Gray
//                    )
//                }
//            }
//
//            //Informacion de Duracion y calificacion
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                //Duracion
//                Column {
//                    Row(verticalAlignment = Alignment.CenterVertically){
//                        Icon(
//                            imageVector = Icons.Default.DateRange,
//                            contentDescription = "Duración",
//                            tint = Color.Gray,
//                            modifier = Modifier.size(20.dp)
//                        )
//                        Text(
//                            text = "Duración: ${movie.runtime} min",
//                            fontSize = 14.sp,
//                            color = Color.Gray,
//                            modifier = Modifier.padding(start = 4.dp)
//                        )
//                    }
//
//                    //Calificacion
//
//                    Column(horizontalAlignment = Alignment.End){
//                        Row (verticalAlignment = Alignment.CenterVertically){
//                            Icon(
//                                imageVector = Icons.Default.Star,
//                                contentDescription = "Calificación",
//                                tint = Color.Gray,
//                                modifier = Modifier.size(20.dp)
//                            )
//                            Text(
//                                text = "Calificación: ${movie.vote_average} / 10",
//                                fontSize = 14.sp,
//                                color = Color.Gray,
//                                modifier = Modifier.padding(start = 4.dp)
//                            )
//                        }
//
//                        Text(
//                            text = "Votos: ${movie.vote_count}",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(top = 4.dp)
//                        )
//                    }
//                }
//
//                //Descripcion
//                Text(
//                    text = movie.overview,
//                    fontSize = 14.sp,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(top = 16.dp)
//                )
//
//                //boton de visitar
//                Button(
//                    onClick = {},
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 24.dp)
//                        .height(56.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary
//                    ),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Text(
//                        text = "Visitar",
//                        color = Color.White,
//                        fontSize = 16.sp
//                    )
//                }
//            }
//        }
    }
}