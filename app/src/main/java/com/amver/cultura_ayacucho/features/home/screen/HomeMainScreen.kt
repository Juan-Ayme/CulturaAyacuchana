    package com.amver.cultura_ayacucho.features.home.screen

    import android.util.Log
    import androidx.compose.foundation.background
    import androidx.compose.foundation.gestures.detectTapGestures
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
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.vector.ImageVector
    import androidx.compose.ui.input.pointer.pointerInput
    import androidx.compose.ui.platform.LocalFocusManager
    import androidx.compose.ui.res.vectorResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import com.amver.cultura_ayacucho.R
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
        val placesCategoryAndProvince = viewModel.placesByCategoryAndProvince.toList()
        val placesProvince = viewModel.placesByProvince.toList()
        val query = viewModel.searchQuery

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
                if (query.collectAsState().value.isEmpty()){
                    when(selectCategory){
                        "Popular" ->{
                            Log.e("HomeMainScreen", "selectCategory: $selectCategory")
                            PopularPlacesSection(places,navController,viewModel,selectCategory)
                            PopularProvinceScreen(placesProvince,navController,viewModel)
                        }
                        else->{
                            PopularPlacesSection(
                                places,
                                navController,
                                viewModel,
                                selectCategory,
                                )
                            if (selectCategory == null) {
                                PopularProvinceScreen(placesProvince,navController,viewModel)

                            }else{
                                PlacesByCategoryAndProvinceSection( placesCategoryAndProvince,navController)
                            }
                        }
                    }
                }else{
                   PopularPlacesSection(places,navController,viewModel,selectCategory,true )
                }

            }
        }
    }

    @Composable
    fun PopularPlacesSection(places: List<PlacesItem>,
                             navController: NavController,
                             viewModel: HomeMainViewModel,
                             category:String?,
                             queryState:Boolean = false,
                            ) {

        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 8.dp)
                //Para que se pueda hacer clic en cualquier parte de la pantalla y se quite el foco
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            when(queryState){
                true->{
                    //Espacio entre los lugares populares y el buscar
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                else->{
                    PlacesHeaderText("Lugares populares  ${if (category == "Popular") "" else category?: ""}") {
                        //TODO: Implementar la navegación
                        //navegar a todas las pantallas
                    }
                }
            }

            val uiState by viewModel.uiState.collectAsState()

            when (val state = uiState){
                is ApiState.Success ->{
                    when(queryState){
                        true->{
                            LazyColumn (
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                //Mostrar los lugares encontrados
                                items(places){place->
                                    PopularPlaceCard(place,navController)
                                }
                            }
                        }
                        else->{
                            LazyRow (
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ){
                                //Mostrar los lugares populares con un máximo de 5
                                items(places.take(5)){place->
                                    PopularPlaceCard(place,navController)
                                }
                            }
                        }
                    }
                }
                is ApiState.Error ->{
                    ErrorPopularPlaceScreen(state.message) {
                        viewModel.retryFetchPlacesPopular();
                    }
                }
                is ApiState.Loading ->{
                    LoadingPlaceCardScreen(queryState)
                }
                else ->{
                    Log.e("HomeMainScreen", "Error desconocido")
                }
            }

        }
    }

    @Composable
    fun PlacesByCategoryAndProvinceSection(
        places: List<PlacesItem>,
        navController: NavController,
        viewModel: HomeMainViewModel = viewModel()
    ){
        val selectedProvince by viewModel.selectedProvince

        Column (modifier = Modifier.fillMaxWidth()
        ){
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(HomeMainViewModel.Provinces.name){
                    province ->
                    ProvinceButton(province = province,selectedProvince == province){
                        viewModel.setProvince(province)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //Mostrar lugares de la provinica selecionada
            if (places.isEmpty()){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_map),
                            contentDescription = "Info",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "En la categoria ${viewModel.selectedCategory.value}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Text(
                                text = "no hay lugares para $selectedProvince",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }else{
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(places){
                        place ->
                        PopularPlaceCard(place,navController)
                    }
                }
            }
        }
    }

    @Composable
    fun ProvinceButton(
        province: String,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
            TextButton(
                onClick = onClick,
                modifier = Modifier
                    .padding(0.dp)
                    .background(
                        color = if (isSelected) Color(0xFF0A9396) else Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = province,
                    color = if (isSelected) Color.White else Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
            }
    }

    @Composable
    fun PopularProvinceScreen(
        placesProvince: List<PlacesItem>,
        navController: NavController,
        viewModel: HomeMainViewModel = viewModel()
    ){
        val selectedProvince by viewModel.selectedProvince

        Column (modifier = Modifier.fillMaxWidth()
        ){
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(HomeMainViewModel.Provinces.name){
                        province ->
                    ProvinceButton(province = province,selectedProvince == province){
                        viewModel.setProvince(province)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //Mostrar lugares de la provinica selecionada
            if (placesProvince.isEmpty()){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_map),
                            contentDescription = "Info",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "En la categoria ${viewModel.selectedCategory.value}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Text(
                                text = "no hay lugares para $selectedProvince",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }else{
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(placesProvince){
                            place ->
                        PopularPlaceCard(place,navController)
                    }
                }
            }
        }
    }