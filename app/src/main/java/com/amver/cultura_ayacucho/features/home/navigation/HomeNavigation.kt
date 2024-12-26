import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.explore.screen.ExploreMainScreen
import com.amver.cultura_ayacucho.features.favorite.navigation.NavigationFavoritePlace
import com.amver.cultura_ayacucho.features.favorite.screen.FavoriteMainScreen
import com.amver.cultura_ayacucho.features.home.components.bottom_bar.BottomBarComponent
import com.amver.cultura_ayacucho.features.home.navigation.PlaceCategoryNavigation
import com.amver.cultura_ayacucho.features.home.navigation.PlaceDetailScreen
import com.amver.cultura_ayacucho.features.home.screen.HomeMainScreen
import com.amver.cultura_ayacucho.features.login.screen.LoginScreen
import com.amver.cultura_ayacucho.features.login.screen.LoginScreenEX
import com.amver.cultura_ayacucho.features.register.screen.RegistrationScreen
import com.amver.cultura_ayacucho.features.register.screen.SuccessFulRegistrationScreen
import com.amver.cultura_ayacucho.features.user.screen.UserMainScreen

/**
 * HomeView es el contenedor principal de la aplicación
 * */
@Composable
fun HomeNavigation(){
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
        composable(ScreenNavigation.Favorite.route) {
            ScaffoldScreen(navController) { FavoriteMainScreen(navController=navController) }
        }
        composable(ScreenNavigation.User.route) {
            ScaffoldScreen(navController) { UserMainScreen(navController = navController) }
        }

        composable(ScreenNavigation.Registration.route) {
            RegistrationScreen(navController = navController)
        }

        composable(ScreenNavigation.Login.route) {
            LoginScreenEX(navController = navController)
        }

        composable(ScreenNavigation.SuccessfulRegistration.route) {
            SuccessFulRegistrationScreen(navController)
        }

        //navegar por detalle de lugar
        composable(
            route = ScreenNavigation.PlaceDetail.route, // ruta con argumento
            arguments = listOf(
                navArgument("placeId") { type = NavType.IntType },
                navArgument("isFavorite") { type = NavType.BoolType }
            )
        ) {
            navBackStackEntry ->
            val placeId = navBackStackEntry.arguments?.getInt("placeId") // obtenemos el argumento
            val isFavorite = navBackStackEntry.arguments?.getBoolean("isFavorite") // obtenemos el argumento
            PlaceDetailScreen(
                placeId = placeId!!,
                navController = navController,
                isFovorite = isFavorite!!
            )
        }
        //navegar por categoría
        composable(
            route = ScreenNavigation.PlaceByCategory.route, // ruta con argumento
            arguments =  listOf(navArgument("category") { type = NavType.StringType }) // argumento de tipo entero
        ) { navBackStackEntry ->
            val category = navBackStackEntry.arguments?.getString("category") // obtenemos el argumento

            ScaffoldScreen(navController) {
                PlaceCategoryNavigation(category = category!!,navController = navController)
            }
        }

        //navegar por detalle de lugar favorito

        composable(
            route = ScreenNavigation.PlaceFavoriteDetail.route,// ruta con argumento
            arguments = listOf(navArgument("placeId") { type = NavType.IntType },
                            navArgument("isFavorite") { type = NavType.BoolType }) // argumento de tipo entero
        ){
            navBackStackEntry ->
            val placeId = navBackStackEntry.arguments?.getInt("placeId") // obtenemos el argumento
            val isFavorite = navBackStackEntry.arguments?.getBoolean("isFavorite") // obtenemos el argumento
            NavigationFavoritePlace(placeId = placeId!!, isFavorite = isFavorite!!, navController = navController)
        }

    }
}

@Composable
fun ScaffoldScreen(
    navController: NavController,
    content: @Composable () -> Unit // contenido de la pantalla
){
    Scaffold (
        bottomBar = {
            BottomBarComponent(navController = navController)
        }
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            content() // contenido de la pantalla
        }
    }
}