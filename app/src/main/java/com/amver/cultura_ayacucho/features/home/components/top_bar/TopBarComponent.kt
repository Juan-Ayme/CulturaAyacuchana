package com.amver.cultura_ayacucho.features.home.components.top_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.features.home.viewmodel.HomeMainViewModel
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModelMain


/**
 * Este componente es el encargado de mostrar la barra de navegación superior
 */


@Composable
fun TopBarComponent(viewModel: LoginViewModelMain = viewModel()) {

    val username = viewModel.getUsernameFromPreferences()
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Column{
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,//el espacio entre los elementos

            ){
                //Icono de menúe
//                IconButton(
//                    onClick = { /* Acción del botón de menú */ }
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Menu,
//                        contentDescription = "Menu",
//                        tint = Color.White
//                    )
//                }
//                //Texto de bienvenida MODIFICAR CON EL NOMBRE DEL USUARIO
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = " ${if (username==null) "Bienvenido" else "Bienvenido de nuevo, $username"}",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Perú, Ayacucho", fontSize = 14.sp, color = Color.White)
                    }
                }

                //Icono de notificaciones
//                IconButton(
//                    onClick = { /* Acción del ícono de notificación */ }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Notifications,
//                        contentDescription = "Notificaciones",
//                        tint = Color.White
//                    )
//                }
            }
            //Boton de búsqueda
            SearchBar()
        }
    }
}

@Composable
fun SearchBar(viewModel: HomeMainViewModel = viewModel()) {
    val searchQuery = viewModel.searchQuery.collectAsState();
    val categories = getCategories();
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, vertical = 4.dp)
            .pointerInput(Unit){
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { newQuery->
                viewModel.searchPlaces(newQuery)
            },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFE5E9EC),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (searchQuery.value.isEmpty()){
                        Text(
                            text = "Buscar destino",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
    //Botones de categorías
    AnimatedVisibility(
        visible = searchQuery.value.isEmpty(),
        enter = slideInVertically(initialOffsetY = {it})+ fadeIn(),
        exit = slideOutVertically (targetOffsetY ={-it})+ fadeOut()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, vertical = 4.dp)
                .pointerInput(Unit){
                  detectTapGestures(
                      onTap = {
                          focusManager.clearFocus()
                      }
                  )
                },
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            items(categories){category ->
                CategoryButton(
                    icon = category.icon,
                    name = category.name,
                    backgroundColor = Color(0xffffffff),
                ) {
                    focusManager.clearFocus()
                }
            }
        }
    }

}

@Composable
fun getCategories(viewModel: HomeMainViewModel = viewModel()): List<Category>{
    val categories = viewModel.selectedCategory.value
    return listOf(
            Category(
                icon = if (categories.equals("Popular")|| categories.equals(null)) ImageVector.vectorResource(id = R.drawable.icon_star) else ImageVector.vectorResource(id = R.drawable.icon_star_border),
                name = "Popular",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = if (categories.equals("Cultural")) ImageVector.vectorResource(id = R.drawable.icon_iglesia) else ImageVector.vectorResource(id = R.drawable.icon_iglesia_border),
                name = "Cultural",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon =  if (categories.equals("Natural")) ImageVector.vectorResource(id = R.drawable.icon_natural) else ImageVector.vectorResource(id = R.drawable.icon_natural_border),
                name = "Natural",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = if (categories.equals("Gastronomico")) ImageVector.vectorResource(id = R.drawable.icon_food) else ImageVector.vectorResource(id = R.drawable.icon_food_border),
                name = "Gastronomico",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = if (categories.equals("Historico")) ImageVector.vectorResource(id = R.drawable.icon_history) else ImageVector.vectorResource(id = R.drawable.icon_history_border),
                name = "Historico",
                backgroundColor = Color(0xffffffff)
            )
    )
}

data class Category(
    val icon: ImageVector,
    val name: String,
    val backgroundColor: Color
)


@Composable
private fun CategoryButton(
    icon:ImageVector,
    name: String,
    backgroundColor: Color,
    viewModel: HomeMainViewModel = viewModel(),
    onClick: () -> Unit
){
    Button(
        onClick = {
            viewModel.getPlacesByCategory(name)
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = name,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.width(8.dp))
}
