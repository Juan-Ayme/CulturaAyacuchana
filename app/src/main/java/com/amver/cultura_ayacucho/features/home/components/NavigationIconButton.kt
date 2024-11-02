package com.amver.cultura_ayacucho.features.home.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun NavigationIconButton(navController: NavController, route: String, icon: ImageVector, contentDescription: String) {
    IconButton(onClick = {
        navController.navigate(route)
    }) {
        Icon(icon, contentDescription = contentDescription)
    }

}