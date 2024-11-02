package com.amver.cultura_ayacucho.features.home.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Este componente es el encargado de mostrar la barra de navegación superior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun topBarView() {
    CenterAlignedTopAppBar(
        title = { Text("Cultura Ayacucho") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "back")
            }
        }
    )// End CenterAlignedTopAppBar
}