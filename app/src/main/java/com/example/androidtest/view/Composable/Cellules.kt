package com.example.androidtest.view.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidtest.model.Contact
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Cellules(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = "https://avatars.githubusercontent.com/u/583231?v=4",
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(20.dp))
        Column {
            Text(text = contact.nom, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(text = contact.adress, modifier = Modifier.alpha(0.5f))
        }
    }
}