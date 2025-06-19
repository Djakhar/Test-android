package com.example.androidtest.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.CircleShape

@Composable
fun DetailsScreen(name: String, address: String, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/583231?v=4",
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(20.dp))
            Text(text = name, fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            Text(text = address, fontSize = 20.sp)
        }
    }
}