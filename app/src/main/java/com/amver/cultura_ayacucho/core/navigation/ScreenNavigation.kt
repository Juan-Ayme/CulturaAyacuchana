package com.amver.cultura_ayacucho.core.navigation

sealed class ScreenNavigation(val route:String){
    data object Home: ScreenNavigation("home")
    data object Explore: ScreenNavigation("explore")
    data object Favorite: ScreenNavigation("favorite")
    data object User: ScreenNavigation("user")
    data object Login: ScreenNavigation("login")
    data object Registration: ScreenNavigation("registration")
    data object SuccessfulRegistration: ScreenNavigation("successfulRegistration")

    data object PlaceDetail: ScreenNavigation("placeDetail/{placeId}"){
       fun createRoute(placeId: Int) = "placeDetail/$placeId" // función para crear la ruta con el argumento
    }

    data object PlaceByCategory: ScreenNavigation("placeByCategory/{category}"){
        fun createRoute(category: String) = "placeByCategory/$category" // función para crear la ruta con el argumento
    }
}