package com.amver.cultura_ayacucho.features.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.place.PlacesItem
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeMainViewModel

@Composable
fun ExploreMainScreen(viewModel: HomeMainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedProvince by viewModel.selectedProvince
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory

    var searchText by remember { mutableStateOf(TextFieldValue(searchQuery)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchPlaces(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White.copy(alpha = 0.2f)),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchText.text.isEmpty()) {
                        Text("Buscar lugares...", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Selector
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { CategoryChip("Popular", selectedCategory == "Popular") { viewModel.getPlacesByCategory("Popular") } }
            item { CategoryChip("Natural", selectedCategory == "Natural") { viewModel.getPlacesByCategory("Natural") } }
            item { CategoryChip("Cultural", selectedCategory == "Cultural") { viewModel.getPlacesByCategory("Cultural") } }
            item { CategoryChip("Gastronomico", selectedCategory == "Gastronomico") { viewModel.getPlacesByCategory("Gastronomico") } }
            item { CategoryChip("Historico", selectedCategory == "Historico") { viewModel.getPlacesByCategory("Historico") } }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Province Selector
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomeMainViewModel.Provinces.name.forEach { province ->
                item {
                    ProvinceChip(province, selectedProvince == province) {
                        viewModel.setProvince(province)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Places List
        when (uiState) {
            is ApiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ApiState.Success -> {
                val places = (uiState as ApiState.Success<List<PlacesItem>>).data
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(places.size) { index ->
                        PlaceItem(places[index])
                    }
                }
            }
            is ApiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${(uiState as ApiState.Error).message}")
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Estado desconocido")
                }
            }
        }
    }
}

@Composable
fun CategoryChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xff0A9396) else Color(0xff0A9396).copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

        )
    }
}

@Composable
fun ProvinceChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xffBB3E03) else Color(0xffBB3E03).copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
    }
}

@Composable
fun PlaceItem(place: PlacesItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff001219)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(place.name, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = if (place.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (place.isFavorite == true) Color(0xffAE2012) else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(place.location ?: "Descripción no disponible", style = MaterialTheme.typography.bodyMedium) // Handle potential null
        }
    }
}
