package com.example.androidtest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidtest.data.ContactRepository
import com.example.androidtest.factory.ContactViewModelFactory
import com.example.androidtest.view.composable.Cellules
import com.example.androidtest.view.utilitaire.isTablet
import com.example.androidtest.viewmodel.ContactViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color



@Composable
fun ContactListScreen(navController: NavHostController) {
    val repository = ContactRepository()
    val viewModel: ContactViewModel = viewModel(factory = ContactViewModelFactory(repository))
    val tablet= isTablet()
    val contacts = viewModel.contacts.collectAsState().value

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ){
        Text(
            text = "Liste de Contacts",
            modifier = Modifier.fillMaxWidth(),
            fontSize = if (tablet) 48.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(30.dp))
        LazyColumn {
            items(contacts) { contact ->
                Cellules(contact) {
                    navController.navigate("details_screen/${contact.id}")
                }
            }
        }
    }
}


