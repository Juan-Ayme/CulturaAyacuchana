package com.amver.cultura_ayacucho.features.home.components.bottom_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModelMain

/**
 * Este componente es el encargado de mostrar la barra de navegación inferior
 * @param navController es el encargado de la navegación
 */
@Composable
internal fun BottomBarComponent(navController: NavController){
    // Obtenemos la ruta actual
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = null).value?.destination?.route

    val viewModel: LoginViewModelMain = viewModel()

    val isLogged = viewModel.isSuccessFullLogin()


    BottomAppBar(
        modifier = Modifier
            .height(90.dp),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                // Se crean los botones de navegación
                //Icono de Inicio
                NavigationIconButton(navController = navController, route = ScreenNavigation.Home.route,
                    iconBorder = ImageVector.vectorResource(id = R.drawable.icon_home_borde),
                    iconColor = ImageVector.vectorResource(id = R.drawable.icon_home_color),
                    contentDescription = "Inicio", isSelected =  currentRoute == ScreenNavigation.Home.route)
                //Icono de Explorar
                NavigationIconButton(navController = navController, route =  ScreenNavigation.Explore.route,
                    iconBorder = ImageVector.vectorResource(id = R.drawable.icon_compass_border),
                    iconColor = ImageVector.vectorResource(id = R.drawable.icon_compass_color),
                    contentDescription = "Explorar", isSelected =  currentRoute == ScreenNavigation.Explore.route)
                //Icono de Favoritos
                NavigationIconButton(navController = navController, route = ScreenNavigation.Favorite.route,
                    iconBorder = Icons.Default.FavoriteBorder,
                    iconColor = Icons.Default.Favorite,
                    contentDescription = "Favoritos", isSelected =  currentRoute == ScreenNavigation.Favorite.route)
                //Icono de Usuario
                NavigationIconButton(navController = navController, route = if (isLogged) ScreenNavigation.User.route else ScreenNavigation.Login.route,
                    iconColor = ImageVector.vectorResource(id = R.drawable.icon_user_color),
                    iconBorder = ImageVector.vectorResource(id = R.drawable.icon_user_border_light),
                    contentDescription = "Usuario", isSelected = currentRoute == ScreenNavigation.User.route)
            }
        }
    )// BottomAppBar
}
