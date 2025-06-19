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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidtest.data.ContactRepository
import com.example.androidtest.factory.DetailsViewModelFactory
import com.example.androidtest.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(id:Int, navController: NavHostController) {
    val repository= ContactRepository()
    val viewModel: DetailsViewModel= viewModel(factory = DetailsViewModelFactory(repository))
    val contact=viewModel.contact.collectAsState().value

    LaunchedEffect(id) {
        viewModel.loadContactById(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
        contact?.let{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                AsyncImage(
                    model = "https://robohash.org/${it.id}?set=set2",
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(20.dp))
                Text(text = "Nom: ${it.name}", fontSize = 20.sp,)
                Text(text = "Ville: ${it.address.city}", fontSize = 16.sp)
                Text(text = "Email: ${it.email}", fontSize = 16.sp)
            }
        }?: Text("Chargement...", modifier = Modifier.align(Alignment.Center))
    }
}