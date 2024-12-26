package com.amver.cultura_ayacucho.features.home.screen.placesCard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.features.favorite.viewmodel.FavoriteViewModel
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModel


@Composable
fun PopularPlaceCard(placesItem: PlacesItem, navController: NavController,viewModel: FavoriteViewModel= viewModel(),viewModelLogin: LoginViewModel = viewModel()) {

    var isStateFavorite by remember { mutableStateOf(placesItem.isFavorite?:false) }
    val stateLogin = viewModelLogin.loginState.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }

    LaunchedEffect(placesItem.isFavorite) {
        isStateFavorite = placesItem.isFavorite?:false
    }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(250.dp) // Mayor altura para una mejor visibilidad de la imagen
            .clickable {
                //val isFavorite = placesItem.isFavorite == true
                navController.navigate(
                    ScreenNavigation.PlaceDetail.createRoute(
                        placesItem.placeId,
                        isFavorite = isStateFavorite
                    )
                )
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
                                Color.Black.copy(alpha = 0.8f) // Aumento de la opacidad
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
                            text = placesItem.location ?: "Ubicación no disponible",
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
                        // Clasificación o distancia
                        if (placesItem.punctuationAverage != null){
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
                        }else{
                            //distancia
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.icon_map),
                                contentDescription = "Ubicación",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            placesItem.distance?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        }
                    }
            }

            // Favorite button
            IconButton(
                onClick = {
                    if (stateLogin.value?.isSuccess == true) {
                        if (placesItem.isFavorite == true)
                            viewModel.deleteFavoritePlace(placeId = placesItem.placeId)
                        else
                            viewModel.addFavoritePlace(placeId = placesItem.placeId)

                        // Cambiar el estado del favorito al inverso al hacer click
                        isStateFavorite = !isStateFavorite

                    }else{
                        showLoginDialog = true
                    }
              },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(
                    imageVector = if (isStateFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isStateFavorite) Color(0xffAE2012) else Color.White
                )
            }
            if (showLoginDialog){
                Dialog(onDismissRequest = {showLoginDialog = false},
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), // Fondo semi-transparente para el contenido
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)) // Borde sutil
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "Inicia sesión para agregar a favoritos",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        navController.navigate(ScreenNavigation.Login.route)
                                        showLoginDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF005F73)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Iniciar sesión / Registrarse",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
