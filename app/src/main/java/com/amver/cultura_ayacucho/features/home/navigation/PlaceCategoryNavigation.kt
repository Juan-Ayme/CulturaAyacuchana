package com.amver.cultura_ayacucho.features.home.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.features.home.screen.PopularPlacesSection
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeMainViewModel

@Composable

fun PlaceCategoryNavigation(category:String,viewModel: HomeMainViewModel = viewModel(),navController: NavController ) {
    LaunchedEffect(category) {
        Log.e("PlaceCategoryNavigation", "category: buscar $category")
        viewModel.getPlacesByCategory(category)
    }

    val places = viewModel.popularPlaces.toList()
    if (places.isNotEmpty()) {
        PopularPlacesSection(places,navController,viewModel,category)
    } else {
        Log.e("PlaceCategoryNavigation", "category: $category")

        Box(
            modifier = Modifier.fillMaxWidth()
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