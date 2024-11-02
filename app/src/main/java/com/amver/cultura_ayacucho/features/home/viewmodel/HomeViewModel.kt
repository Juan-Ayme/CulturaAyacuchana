import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.model.MovieModel
import com.amver.cultura_ayacucho.features.explore.screen.exploreMainScreen
import com.amver.cultura_ayacucho.features.favorite.screen.favoriteMainScreen
import com.amver.cultura_ayacucho.features.home.navigation.movieDetailScreen
import com.amver.cultura_ayacucho.features.home.screen.homeMainScreen
import com.amver.cultura_ayacucho.features.home.viewmodel.bottomBarView
import com.amver.cultura_ayacucho.features.user.screen.userMainSreen


/**
 * HomeView es el contenedor principal de la aplicación
 * */
@Composable
fun HomeView(){
    val navController = rememberNavController() // el navController es el encargado de la navegación

    Scaffold(
        bottomBar = {bottomBarView(navController)} )
    {paddingValues ->
        NavHost(
            navController = navController, // el navController es el encargado de la navegación
            startDestination = ScreenNavigation.Home.route,
            modifier = Modifier.padding(paddingValues) // añadimos padding para que no se superponga con la barra de navegación
        ) {
            composable(ScreenNavigation.Home.route) { homeMainScreen(navController)}
            composable(ScreenNavigation.Explore.route) { exploreMainScreen() }
            composable(ScreenNavigation.Favorite.route) { favoriteMainScreen()  }
            composable(ScreenNavigation.User.route) { userMainSreen() }
            /**
             *  En esta parte se define la ruta para el detalle de la película
             * */
            composable(
                route= ScreenNavigation.MovieDetail.route,// ruta con argumento
                arguments = listOf(navArgument("movieId"){type = NavType.IntType}) // argumento de tipo entero
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId")// obtenemos el argumento
                if (movieId != null) {
                    movieDetailScreen(movieId = movieId)
                }
            }

        }// End NavHost
    }
}

