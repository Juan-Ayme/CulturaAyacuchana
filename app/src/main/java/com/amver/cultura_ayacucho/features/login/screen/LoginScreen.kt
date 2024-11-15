package com.amver.cultura_ayacucho.features.login.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amver.cultura_ayacucho.features.login.viewmodel.LoginViewModel

data class SignUpStatee(
    val userName: String = "",
    val password: String = "",
)

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    val loginState by viewModel.loginState.collectAsState()

    Column {
        Button(
            onClick = {
                viewModel.loginUser("oso","1234")
            }
        ) {
            Text(
                text = "Login"
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

        }
    }
}