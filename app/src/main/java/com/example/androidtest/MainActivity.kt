package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.androidtest.view.ContactListScreen
import com.example.androidtest.view.DetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "contact_list") {
        composable("contact_list") {
            ContactListScreen(navController)
        }
        composable(
            route = "details_screen/{name}/{adress}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("adress") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val adress = backStackEntry.arguments?.getString("adress") ?: ""
            DetailsScreen(name, adress, navController)
        }
    }
}




