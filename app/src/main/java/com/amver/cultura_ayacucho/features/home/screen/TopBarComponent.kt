package com.amver.cultura_ayacucho.features.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Este componente es el encargado de mostrar la barra de navegación superior
 */
@Composable
internal fun topBarView() {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF001219))
                ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Acción del botón de menú */ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")

            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Bienvenido de nuevo, Juan", fontSize = 18.sp, color = Color.White)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Ubicación")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Perú, Ayacucho", fontSize = 14.sp, color = Color.White)
                }
            }

            IconButton(onClick = { /* Acción del ícono de notificación */ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notificaciones")
            }
        }
    }
}