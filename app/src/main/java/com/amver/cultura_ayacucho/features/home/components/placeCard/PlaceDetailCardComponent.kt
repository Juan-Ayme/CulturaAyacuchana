package com.amver.cultura_ayacucho.features.home.components.placeCard

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.data.model.place.Place

@Composable
fun PlaceDetailCardComponent(
    place: Place,
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }

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
            onClick = {navController.popBackStack()},
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
            onClick = {},
            modifier = Modifier
                .padding(0.dp,40.dp,16.dp,0.dp)
                .align(Alignment.TopEnd)
                .background(Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritos",
                tint = Color.White,
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
                    text = "Comentarios",
                    isSelected = selectedTab == 1,
                    onClick = {selectedTab = 1}
                )
            }

            //contenido basado en la pestaña seleccionada
            when(selectedTab){
                0-> GeneralContentScreen(place)
                1-> CommentsConent()

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
private fun CommentsConent(){
    //Implementar vista de comentarios
    Column{
        Text(
            text = "Comentarios",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
