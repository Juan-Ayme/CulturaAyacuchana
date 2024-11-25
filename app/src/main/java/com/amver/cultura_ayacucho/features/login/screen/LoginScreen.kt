package com.amver.cultura_ayacucho.features.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextField
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextFieldPassword
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModel

data class LoggingStatus(
    val username: String = "",
    val password: String = "",
)

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavController) {
    val loginState by viewModel.loginState.collectAsState()
    var loggingStatus by remember { mutableStateOf(LoggingStatus()) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF1F1F1F),
                    Color.Gray
                )
            ))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
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
                text = "Correo electrónico",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            CustomOutlinedTextField(
                value = loggingStatus.username,
                onValueChange = {loggingStatus = loggingStatus.copy(username = it)},
                placeholderText = "Correo electrónico",
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
                    viewModel.loginUser(
                        loggingStatus.username,
                        loggingStatus.password
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB666D2)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    color = Color.White
                )
            }

            loginState?.let { result ->
                result.onSuccess { response->
                    Text(
                        text = response.message
                    )
                }.onFailure { error ->
                    Text(
                        text = error.message.toString()
                    )
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
                    containerColor = Color(0xFFB666D2)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Regístrate",
                    color = Color.White
                )
            }

            }
        }
    }
}