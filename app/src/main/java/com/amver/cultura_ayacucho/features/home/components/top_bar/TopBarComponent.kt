package com.amver.cultura_ayacucho.features.home.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amver.cultura_ayacucho.R

/**
 * Este componente es el encargado de mostrar la barra de navegación superior
 */


@Preview
@Composable
fun TopBarMain(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF001219))
    ){
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween//el espacio entre los elementos
            ){
                //Icono de menú
                IconButton(
                    onClick = { /* Acción del botón de menú */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                //Texto de bienvenida MODIFICAR CON EL NOMBRE DEL USUARIO
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Bienvenido de nuevo, Juan", fontSize = 18.sp, color = Color.White)
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
                IconButton(
                    onClick = { /* Acción del ícono de notificación */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.White
                    )
                }
            }

            //Boton de búsqueda
            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    val categories = getCategories()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = "",
            onValueChange = { },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFE5E9EC),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buscar destino",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    innerTextField()
                }
            }
        )
    }
    //Botones de categorías

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        items(categories){category ->
            CategoryButton(
                icon = category.icon,
                text = category.text,
                backgroundColor = Color(0xffffffff)
            ) { }
        }
    }
}

@Composable
fun getCategories(): List<Category>{
    return listOf(
            Category(
                icon = ImageVector.vectorResource(id = R.drawable.icon_iglesia),
                text = "Cultural",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = ImageVector.vectorResource(id = R.drawable.icon_natural_border),
                text = "Natural",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = ImageVector.vectorResource(id = R.drawable.icon_food_border),
                text = "Gastronómico",
                backgroundColor = Color(0xffffffff)
            ),
            Category(
                icon = ImageVector.vectorResource(id = R.drawable.icon_history_border),
                text = "Histórico",
                backgroundColor = Color(0xffffffff)
            )
    )
}

data class Category(
    val icon: ImageVector,
    val text: String,
    val backgroundColor: Color
)


@Composable
private fun CategoryButton(
    icon:ImageVector,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
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
                contentDescription = text,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = text,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.width(8.dp))
}