package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtest.ui.theme.AndroidTestTheme
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun ContactList() {
    val items = (1..10).toList()
    LazyColumn {
        items(items){
            Cellules()
        }
    }
}


@Composable
fun Cellules() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)){
       Image(
           painter = painterResource(R.drawable.ic_launcher_background),
           contentDescription = "Image d'un contact"
        )
        Spacer(Modifier.width(20.dp))
        Column {
            Text(text="Nom Prenom")
            Spacer(Modifier.height(4.dp))
            Text(text="adresse")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {
        ContactList()
    }
}