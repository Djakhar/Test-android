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
import com.example.androidtest.view.composable.Cellules
import com.example.androidtest.viewmodel.ContactViewModel
import java.net.URLEncoder

@Composable
fun ContactListScreen(navController: NavHostController, viewModel: ContactViewModel = viewModel()) {
    val contacts = viewModel.contacts.collectAsState().value

    Column {
        Text(
            text = "Liste de Contacts",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))
        LazyColumn {
            items(contacts) { contact ->
                Cellules(contact) {
                    val name = URLEncoder.encode(contact.nom, "UTF-8").replace("+", "%20")
                    val address = URLEncoder.encode(contact.adress, "UTF-8").replace("+", "%20")
                    navController.navigate("details_screen/$name/$address")
                }
            }
        }
    }
}

