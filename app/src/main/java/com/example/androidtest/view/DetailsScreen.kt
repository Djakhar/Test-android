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
import com.example.androidtest.view.utilitaire.isTablet
import com.example.androidtest.viewmodel.DetailsViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background

@Composable
fun DetailsScreen(id:Int, navController: NavHostController) {
    val repository= ContactRepository()
    val viewModel: DetailsViewModel= viewModel(factory = DetailsViewModelFactory(repository))
    val contact=viewModel.contact.collectAsState().value
    val tablet= isTablet()

    LaunchedEffect(id) {
        viewModel.loadContactById(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(if (tablet) 52.dp else 32.dp)
    ) {
        Button(
            modifier = Modifier
                .height(if (tablet) 70.dp else 55.dp)
                .width(if (tablet) 150.dp else 95.dp),
            onClick = { navController.popBackStack() }) {
            Text(
                text="Back",
                fontSize = if (tablet) 28.sp else 16.sp)
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
                        .size(if (tablet) 350.dp else 200.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(20.dp))
                Text(text = "Nom: ${it.name}",
                    fontSize = if (tablet) 34.sp else 20.sp,
                    color = MaterialTheme.colorScheme.onBackground )
                Text(text = "Ville: ${it.address.city}",
                    fontSize = if (tablet) 30.sp else 16.sp,
                    color = MaterialTheme.colorScheme.onBackground )
                Text(text = "Email: ${it.email}",
                    fontSize = if (tablet) 30.sp else 16.sp,
                    color = MaterialTheme.colorScheme.onBackground )
            }
        }?: Text("Chargement...", modifier = Modifier.align(Alignment.Center))
    }
}