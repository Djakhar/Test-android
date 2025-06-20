package com.example.androidtest.view.composable

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
import androidx.compose.ui.unit.sp
import com.example.androidtest.view.utilitaire.isTablet
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@Composable
fun Cellules(contact: Contact, onClick: () -> Unit) {

    val tablet= isTablet()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (tablet) 16.dp else 8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = "https://robohash.org/${contact.id}?set=set2",
            contentDescription = "Avatar",
            modifier = Modifier
                .size(if (tablet) 96.dp else 64.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(if (tablet) 24.dp else 20.dp))
        Column {
            Text(text = contact.nom,
                fontWeight = FontWeight.Bold,
                fontSize = if (tablet) 24.sp else 16.sp,
                color = MaterialTheme.colorScheme.onBackground )
            Spacer(Modifier.height(4.dp))
            Text(text = contact.adress,
                modifier = Modifier.alpha(0.5f),
                fontSize = if (tablet) 18.sp else 14.sp,
                color = MaterialTheme.colorScheme.onBackground )
        }
    }
}