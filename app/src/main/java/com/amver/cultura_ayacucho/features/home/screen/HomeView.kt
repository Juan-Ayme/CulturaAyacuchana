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
import com.amver.cultura_ayacucho.features.favorite.screen.FavoriteMainScreen
import com.amver.cultura_ayacucho.features.home.navigation.MovieDetailScreen
import com.amver.cultura_ayacucho.features.home.screen.HomeMainScreen
import com.amver.cultura_ayacucho.features.home.screen.BottomBarView
import com.amver.cultura_ayacucho.features.login.screen.LoginScreen
import com.amver.cultura_ayacucho.features.login.screen.RegistrationScreen
import com.amver.cultura_ayacucho.features.user.screen.UserMainSreen

/**
 * HomeView es el contenedor principal de la aplicación
 * */
@Composable
fun HomeView(){
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
            ScaffoldScreen(navController) { FavoriteMainScreen() }
        }
        composable(ScreenNavigation.User.route) {
            ScaffoldScreen(navController) { UserMainSreen() }
        }

        composable(ScreenNavigation.Registration.route) {
            RegistrationScreen(navController = navController)
        }

        composable(ScreenNavigation.Login.route) {
            LoginScreen(navController = navController)
        }

        // En esta parte se define la ruta para el detalle de la película
        composable(
            route = ScreenNavigation.MovieDetail.route, // ruta con argumento
            arguments = listOf(navArgument("movieId") { type = NavType.IntType }) // argumento de tipo entero
        ) { navBackStackEntry ->

            val movieId = navBackStackEntry.arguments?.getInt("movieId") // obtenemos el argumento

            MovieDetailScreen(
                movieId = movieId!!,
                navController = navController
            )
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
            BottomBarView(navController = navController)
        }
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            content() // contenido de la pantalla
        }
    }
}

