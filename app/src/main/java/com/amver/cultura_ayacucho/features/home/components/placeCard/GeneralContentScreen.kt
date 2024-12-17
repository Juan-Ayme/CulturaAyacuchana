package com.amver.cultura_ayacucho.features.home.components.placeCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amver.cultura_ayacucho.R
import com.amver.cultura_ayacucho.data.model.place.Place


@Composable
fun GeneralContentScreen(place: Place){
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            //Duracion
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_clock),
                    contentDescription = "Duracion",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Duracion",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        text = "2 horas",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            //Clasificacion
            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Clasificacion",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(20.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Clasificacion",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${place.qualificationAverage}",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

        //Descripcion
        Text(
            text = place.description,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Boton visitar ahora
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff005F73)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Visitar ahora",
                color = Color.White,
                fontSize = 16.sp
            )
        }

    }
}
