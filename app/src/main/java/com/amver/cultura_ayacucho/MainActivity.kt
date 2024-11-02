package com.amver.cultura_ayacucho

import HomeView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.amver.cultura_ayacucho.ui.theme.Cultura_ayacuchoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val service = RetrofitServiceFactory.makeRetrofitService()
//        lifecycleScope.launch {
//            val movies = service.listPopularMovies("10a194a01f1ef814a37ded893c4c9221","US")
//            println(movies)
//        }
        setContent {
            Cultura_ayacuchoTheme {
               HomeView()
            }
        }
    }
}
