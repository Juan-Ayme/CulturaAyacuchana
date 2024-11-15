package com.amver.cultura_ayacucho.features.user.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.amver.cultura_ayacucho.R

@Composable
fun UserMainSreen() {
    Image(
        painter = painterResource(id = R.drawable.aa),
        contentDescription = "Ayacucho",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Text(text = "ScreenMainUser")
}