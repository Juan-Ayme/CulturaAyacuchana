package com.amver.cultura_ayacucho.features.favorite.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.features.favorite.components.PlaceDetailCardFavorite
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeMainViewModel

@Composable
fun NavigationFavoritePlace(placeId: Int, isFavorite: Boolean?, viewModel: HomeMainViewModel = viewModel(), navController: NavController) {

    LaunchedEffect(placeId) {
        Log.e("NavigationFavoritePlace", "placeId: buscar $placeId")
        viewModel.getPlaceById(placeId)
    }

    val place by viewModel.selectedPlaceState.collectAsState()

    if(place != null) {
        PlaceDetailCardFavorite(place = place!!, navController = navController, isFavorite = isFavorite)
    } else {
        Log.e("NavigationFavoritePlace", "placeId: $placeId")

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color(0xFF00BFA6),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}