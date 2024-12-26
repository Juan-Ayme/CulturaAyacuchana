package com.amver.cultura_ayacucho.features.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextField
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextFieldPassword
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModelEx


data class LoggingStatusEX(
    val username: String = "",
    val password: String = "",
)

@Composable
fun LoginScreenEX(viewModel: LoginViewModelEx = viewModel(), navController: NavController) {
    //val loginState = viewModel.loginState.value // el estado de la petición de login
    var loggingStatus by remember { mutableStateOf(LoggingStatusEX()) }
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){

        IconButton(
            onClick = {
                navController.navigate(ScreenNavigation.Home.route)
            },
            modifier = Modifier
                .padding(16.dp,40.dp, 0.dp, 0.dp)
                //.align(Alignment.TopStart)
                .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(50))
                .size(40.dp)

        ) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.icon_back),
                contentDescription = "Regresar",
                tint = Color.White,
                modifier = Modifier.size(25.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.login_person),
                contentDescription = "Login Person",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "¡Inicia El Viaje!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Usuario",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            CustomOutlinedTextField(
                value = loggingStatus.username,
                onValueChange = {loggingStatus = loggingStatus.copy(username = it)},
                placeholderText = "Usuario",
                leadingIconPainter = painterResource(id = R.drawable.icon_person),
                leadingIconDesciption = " User"
            )
            Text(
                text = "Contraseña",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            CustomOutlinedTextFieldPassword(
                value = loggingStatus.password,
                // el it es el valor que se esta pasando
                onValueChange = {loggingStatus = loggingStatus.copy(password = it)},
                placeholderText = "Contraseña",
                leadingIconPainter = painterResource(id = R.drawable.icon_lock),
                leadingIconDesciption = "Lock",
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    viewModel.loginUserEX(
                        loggingStatus.username,
                        loggingStatus.password,
                        navController
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A9396)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            when(val state = uiState){
                is ApiState.Success -> {
                    navController.navigate(ScreenNavigation.User.route)
                }
                is ApiState.Error -> {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
                is ApiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(0xFF00BFA6),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                else -> {}
            }

            Text(
                text = "¿No tienes una cuenta?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Button(
                onClick = {
                    navController.navigate(ScreenNavigation.Registration.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A9396)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Regístrate",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

        }
    }
}