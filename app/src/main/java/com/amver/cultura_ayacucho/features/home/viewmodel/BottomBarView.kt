package com.amver.cultura_ayacucho.features.home.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.home.components.NavigationIconButton

/**
 * Este componente es el encargado de mostrar la barra de navegación inferior
 * @param navController es el encargado de la navegación
 */
@Composable
internal fun bottomBarView(navController: NavController){
    BottomAppBar(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp)),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                // añadimos los botones de navegación
                NavigationIconButton(navController, ScreenNavigation.Home.route, Icons.Default.Home, "Home icon")
                NavigationIconButton(navController, ScreenNavigation.Explore.route, Icons.Default.LocationOn, "Explore icon")
                NavigationIconButton(navController,ScreenNavigation.Favorite.route,Icons.Default.FavoriteBorder, "Favorite icon")
                NavigationIconButton(navController, ScreenNavigation.User.route, Icons.Default.AccountCircle, "User icon")
            }
        }
    )
}
