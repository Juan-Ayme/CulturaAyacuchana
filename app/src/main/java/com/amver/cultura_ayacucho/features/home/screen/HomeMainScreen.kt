package com.amver.cultura_ayacucho.features.home.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.features.home.components.PlacesHeaderText
import com.amver.cultura_ayacucho.features.home.screen.placesCard.PopularPlaceCard
import com.amver.cultura_ayacucho.features.home.components.top_bar.TopBarComponent
import com.amver.cultura_ayacucho.features.home.screen.placesCard.ErrorPopularPlaceScreen
import com.amver.cultura_ayacucho.features.home.screen.placesCard.LoadingPlaceCardScreen
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeMainViewModel

@Composable
fun HomeMainScreen(navController: NavController, viewModel: HomeMainViewModel = viewModel()) {
    val places = viewModel.popularPlaces.toList()
    val selectCategory = viewModel.selectedCategory.value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { TopBarComponent() }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            when(selectCategory){
                "Popular" ->{
                    Log.e("HomeMainScreen", "selectCategory: $selectCategory")
                    PopularPlacesSection(places,navController,viewModel,selectCategory)
                }
                else->{
                    PopularPlacesSection(places,navController,viewModel,selectCategory)
                }
            }
        }
    }
}

@Composable
fun PopularPlacesSection(places: List<PlacesItem>, navController: NavController,viewModel: HomeMainViewModel,category:String?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PlacesHeaderText("Lugares populares ${category?:""}") {
            //TODO: Implementar la navegación
            //navegar a todas las pantallas
        }
        Spacer(modifier = Modifier.padding(10.dp))

        val uiState by viewModel.uiState.collectAsState()

        when (val state = uiState){
            is ApiState.Success ->{
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(places){place->
                        PopularPlaceCard(place,navController)
                    }
                }
            }
            is ApiState.Error ->{
                ErrorPopularPlaceScreen(state.message) {
                    viewModel.retryFetchPlacesPopular();
                }
            }
            is ApiState.Loading ->{
                LoadingPlaceCardScreen()
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))
        PlacesHeaderText("Huamanga") {
            //TODO: Implementar la navegación
            //navegar a todas las pantallas
        }

    }
}