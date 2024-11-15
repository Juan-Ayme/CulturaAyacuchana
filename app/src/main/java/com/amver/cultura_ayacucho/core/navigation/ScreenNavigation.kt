package com.amver.cultura_ayacucho.core.navigation

sealed class ScreenNavigation(val route:String){
    object Home: ScreenNavigation("home")
    object Explore: ScreenNavigation("explore")
    object Favorite: ScreenNavigation("favorite")
    object User: ScreenNavigation("user")
    object Start: ScreenNavigation("start")
    data object Login: ScreenNavigation("login")

    object MovieDetail: ScreenNavigation("movieDetail/{movieId}"){
        fun createRoute(movieId: Int) = "movieDetail/$movieId" // función para crear la ruta con el argumento
    }
}