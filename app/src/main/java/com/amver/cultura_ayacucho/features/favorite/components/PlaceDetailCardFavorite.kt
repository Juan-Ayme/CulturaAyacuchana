package com.amver.cultura_ayacucho.features.favorite.components

import com.amver.cultura_ayacucho.features.home.components.placeCard.GeneralContentScreen
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.model.place.Place
import com.amver.cultura_ayacucho.features.favorite.viewmodel.FavoriteViewModel

@Composable
fun PlaceDetailCardFavorite(
    place: Place,
    navController: NavController,
    viewModel: FavoriteViewModel = viewModel(),
    isFavorite: Boolean?
) {

    var selectedTab by remember { mutableIntStateOf(0) }

    //Estado local para el favorito y si isFavorite es nulo, se inicializa en false
    var isStateFavorite by remember { mutableStateOf(isFavorite?:false) }

    var showLoginDialog by remember { mutableStateOf(false) }



    Log.e("PlaceDetailCardComponent", "isFavorite: $isFavorite")

    LaunchedEffect(isFavorite) {
        isStateFavorite = isFavorite?:false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1B1F))
    ){
        //Imagen principal con Bordes redondeados

        AsyncImage(
            model = place.images.firstOrNull(),
            contentDescription = place.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)),
            contentScale = ContentScale.Crop
        )

        //Boton de regreso (back)
        IconButton(
            onClick = {
                navController.navigate(ScreenNavigation.Favorite.route)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp,40.dp,0.dp,0.dp)
                .background(Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                .size(40.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.icon_back),
                contentDescription = "Regresar",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }

        //Boton de favoritos (favorite)

        IconButton(
            onClick = {
                if (isFavorite == true)
                    viewModel.deleteFavoritePlace(place.placeId)
                else
                    viewModel.addFavoritePlace(place.placeId)

                //Cambiar el estado del favorito al inverso al hacer click
                isStateFavorite =! isStateFavorite
            },
            modifier = Modifier
                .padding(0.dp,40.dp,16.dp,0.dp)
                .align(Alignment.TopEnd)
                .background(Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritos",
                tint = if (isStateFavorite) Color(0xFF00BFA6) else Color.White,
            )
        }

        //Contenido principal

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFF1C1B1F))
                .padding(24.dp)
        ){
            Text(
                text = "",
                fontSize = 16.sp,
                color = Color.Gray
            )
            //Titulo
            Text(
                text = place.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            //Ubicacion
            Row (
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicacion",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = place.location,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            //Tabs de navegacion
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            ) {
                TabButton(
                    text = "General",
                    isSelected = selectedTab == 0,
                    onClick = {selectedTab = 0}
                )
                TabButton(
                    text = "Galeria",
                    isSelected = selectedTab == 1,
                    onClick = {selectedTab = 1}
                )
            }

            //contenido basado en la pestaña seleccionada
            when(selectedTab){
                0-> GeneralContentScreen(place)
                1-> GalleryContent(place.images)

            }

        }
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xff005F73) else Color.Transparent
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (isSelected) Color.White else Color.Gray
        )
    }
}


@Composable
private fun GalleryContent(images: List<String>) {

    var selectImage by remember { mutableStateOf<String?>(null) }

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images) { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectImage = imageUrl },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    if (selectImage!=null){
        Dialog(
            onDismissRequest = {selectImage = null}
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            ){
                AsyncImage(
                    model = selectImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(  )
                        .clickable { selectImage = null },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

