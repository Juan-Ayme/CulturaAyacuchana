package com.amver.cultura_ayacucho.features.favorite.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.favorite.components.FavoritePlaceCardComponent
import com.amver.cultura_ayacucho.features.favorite.viewmodel.FavoriteViewModel

@Composable
fun FavoriteMainScreen(viewModel: FavoriteViewModel= viewModel(),navController: NavController) {
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val favoritePlaces = viewModel.favoritePlaces.toList()

    // Para obtener los lugares favoritos al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.getFavoritePlaces()
    }
   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(16.dp)
   ) {
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .wrapContentSize(Alignment.Center)
       ) {
           Text(
               text = "Mis lugares favoritos",
               style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
               color = Color.White
           )
       }
       if (favoritePlaces.isNotEmpty()) {
           LazyColumn {
               items(favoritePlaces) { place ->
                   FavoritePlaceCardComponent(place = place, isFavorite = true, onFavoriteClick = {
                       //viewModel.toggleFavoritePlace(place)
                   }, navController = navController)
               }
           }
         } else if (isLoading) {
              Box(
                modifier = Modifier
                     .fillMaxSize()
                     .padding(16.dp),
                contentAlignment = Alignment.Center
              ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.icon_love_broken_broder),
                                contentDescription = "No favorites",
                                tint = Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = "No tienes lugares favoritos",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            //un boton para que le lleve al inciio
                            Button(
                                onClick = { navController.navigate(ScreenNavigation.Home.route) },
                                modifier = Modifier.padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0A9396)
                                ),
                            ) {
                                Text(
                                    text = "Explorar lugares",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
              }
       } else {
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(16.dp),
               contentAlignment = Alignment.Center
           ) {
               Card(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(16.dp),
                   colors = CardDefaults.cardColors(containerColor = Color.Transparent),
               ) {
                   Column(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(16.dp),
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center
                   ) {
                       Icon(
                           imageVector = ImageVector.vectorResource(id = R.drawable.icon_love_broken_broder),
                           contentDescription = "No favorites",
                           tint = Color.White,
                           modifier = Modifier.size(64.dp)
                       )
                       Button(
                           onClick = { navController.navigate(ScreenNavigation.Login.route) },
                           modifier = Modifier.padding(top = 8.dp),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color(0xFF0A9396)
                           ),
                       ) {
                           Text(
                               text = "Iniciar sesión / Registrarse",
                               style = MaterialTheme.typography.bodyMedium,
                               color = Color.White,
                               fontWeight = FontWeight.Bold
                           )
                       }
                   }
               }
           }
       }
   }
}