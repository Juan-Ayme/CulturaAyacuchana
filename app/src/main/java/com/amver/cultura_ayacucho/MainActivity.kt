package com.amver.cultura_ayacucho

import HomeNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amver.cultura_ayacucho.features.login.screen.LoginScreenEX
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModelEx
import com.amver.cultura_ayacucho.ui.theme.Cultura_ayacuchoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa
        setContent {
            Cultura_ayacuchoTheme {

               HomeNavigation()

                //LoginScreenEX(LoginViewModelEx(application), navController = navController)
                //LoginScreen()
                //HomeMainScreen()
            }
        }
    }
}
