package com.amver.cultura_ayacucho.features.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.explore.screen.ExploreMainScreen
import com.amver.cultura_ayacucho.features.login.screen.LoginScreen


@Composable
internal fun ExperimentalBottomBarView(){
    val navController = rememberNavController() // el navController es el encargado de la navegación

    NavHost(
        navController = navController, // el navController es el encargado de la navegación
        startDestination = ScreenNavigation.Home.route,
    ){
        composable(ScreenNavigation.Home.route) {
            ScaffoldScreen(navController) { HomeMainScreen(navController) }
        }
        composable(ScreenNavigation.Explore.route) {
            ScaffoldScreen(navController) { ExploreMainScreen() }
        }
        composable(ScreenNavigation.Start.route) {
            //LoginScreen()
        }
    }
}

@Composable
fun ScaffoldScreen(
    navController: NavController,
    content: @Composable () -> Unit
){
    Scaffold(
        bottomBar = {
            bottomBarView(navController = navController)
        }
    ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            content()
        }
    }
}