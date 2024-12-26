package com.amver.cultura_ayacucho.features.register.screen

import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.core.navigation.ScreenNavigation
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextField
import com.amver.cultura_ayacucho.features.login.components.CustomOutlinedTextFieldPassword
import com.amver.cultura_ayacucho.features.register.viewmodel.RegistrationViewModel


data class RegistrationStatus(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val fullName: String =""
)

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel = viewModel(), navController: NavController) {

    var singUpState by remember { mutableStateOf(RegistrationStatus()) }
    val errorState by viewModel.errorState.collectAsState()
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){

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
      ) {

          // character image
          Image(
              painter = painterResource(id = R.drawable.login_person),
              contentDescription = "Login Person",
              modifier = Modifier
                  .size(120.dp)
                  .padding(bottom = 16.dp)
          )

          //title and subtitle
          Text(
              text = "¡Empieza el viaje!",
              style = MaterialTheme.typography.headlineMedium,
              color = Color.White
          )
          Text(
              text = "Crear una cuenta para empezar",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White
          )

          Spacer(modifier = Modifier.height(16.dp))

          Text(
              text = "Correo electrónico",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White,
              modifier = Modifier.fillMaxWidth()
          )
          //Email Input
          CustomOutlinedTextField(
              value = singUpState.email,
              onValueChange = {singUpState = singUpState.copy(email = it)},
              placeholderText = "email",
              leadingIconPainter = painterResource(id = R.drawable.icon_email),
              leadingIconDesciption = "Email"
          )

          Text(
              text = "Nombre completo",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White,
              modifier = Modifier.fillMaxWidth()
          )

          CustomOutlinedTextField(
              value = singUpState.fullName,
              onValueChange = {singUpState = singUpState.copy(fullName = it)},
              placeholderText = "nombre completo",
              leadingIconPainter = painterResource(id = R.drawable.icon_person),
              leadingIconDesciption = "Person"
          )

          Text(
              text = "Usuario",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White,
              modifier = Modifier.fillMaxWidth()
          )
          //username Input
          CustomOutlinedTextField(
              value = singUpState.username,
              onValueChange = {singUpState = singUpState.copy(username = it)},
              placeholderText = "nombre de usuario",
              leadingIconPainter = painterResource(id = R.drawable.icon_person),
              leadingIconDesciption = "Person"
          )


          Text(
              text = "Contraseña",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White,
              modifier = Modifier.fillMaxWidth()
          )
          //password Input
          
          CustomOutlinedTextFieldPassword(
              value = singUpState.password,
              onValueChange = {singUpState = singUpState.copy(password = it)},
              placeholderText = "contraseña",
              leadingIconPainter = painterResource(id = R.drawable.icon_lock),
              leadingIconDesciption = "Lock",
              unfocuseBorderColorInput = Color.Transparent,
              focusBorderColor = Color.White.copy(alpha = 0.2f),
              visualTransformation = PasswordVisualTransformation()
          )


          Spacer(modifier = Modifier.height(16.dp))

          //SignUp Button
          Button(
              onClick = {
                  viewModel.registerUser(
                        email = singUpState.email,
                        username = singUpState.username,
                        password = singUpState.password,
                        fullName = singUpState.fullName,
                        navController = navController
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
                  text = "Registrarse",
                  fontSize = 18.sp,
                  color = Color.White
              )
          }

          errorState?.let { error ->
                Log.d("RegistrationScreen", "Error: ${error.errors}")
              Text(
                  text = error.message,
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White,
                  modifier = Modifier
                      .background(Color(255,102,102), shape = RoundedCornerShape(8.dp))
                      .padding(8.dp)
                      .clip(RoundedCornerShape(8.dp))
              )
          }

          Text(
              text = "¿Ya tienes una cuenta?",
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White
          )
            //Login Button
            Button(
                onClick = {
                    navController.navigate(ScreenNavigation.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A9396)
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
      }
    }
}