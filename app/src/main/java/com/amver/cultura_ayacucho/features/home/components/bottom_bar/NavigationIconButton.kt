package com.amver.cultura_ayacucho.features.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NavigationIconButton(navController: NavController, route: String, iconBorder: ImageVector,iconColor: ImageVector , contentDescription: String, isSelected: Boolean) {
    // Color de la barra de navegación
    val color by animateColorAsState(targetValue = if(isSystemInDarkTheme()) Color.White else Color.Black)

    // Color de la línea que indica si el botón está seleccionado
    val lineColor by animateColorAsState(targetValue = if (isSelected) color else Color.Transparent)

    // Icono seleccionado o no, cmabia de icono
    val icon = if(isSelected){
        iconColor
    }else{
        iconBorder
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
        ) {
        IconButton(onClick = { navController.navigate(route){
                launchSingleTop = true // evita que se creen nuevas instancias de la pantalla si ya está abierta
            }
        },
            Modifier.height(28.dp)
        ) {
            Icon(icon, contentDescription = contentDescription, modifier = Modifier.size(25.dp), tint = Color.White)
        }// fin IconButton
        // Línea que indica si el botón está seleccionado
        Text(text = contentDescription, fontSize = 10.sp,color = Color.White)
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(4.dp)
                .background(color = lineColor, shape = RoundedCornerShape(50))
        )
    }
}