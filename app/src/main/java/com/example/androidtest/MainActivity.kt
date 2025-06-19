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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument


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
        composable("contact_list") { ContactList(navController) }
        composable("details_screen/{name}/{adress}",
            arguments = listOf(
                navArgument("name") { type= NavType.StringType },
                navArgument("adress") { type= NavType.StringType }
            )) { backStackEntry->
            val name= backStackEntry.arguments?.getString("name")?:""
            val adress= backStackEntry.arguments?.getString("adress")?:""
            DetailsScreen(name,adress,navController)
        }
    }
}


@Composable
fun ContactList(navController: NavHostController) {
    var listContact=listOf<Contact>()
    listContact=getContactList()
    Column {
        Text(
            text="Liste de Contacts",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(30.dp))
        LazyColumn {
            items(listContact){contact->
                Cellules(contact = contact,onClick={
                    val Name=java.net.URLEncoder.encode(contact.nom,"UTF-8").replace("+", "%20")
                    val Address=java.net.URLEncoder.encode(contact.adress,"UTF-8").replace("+", "%20")
                    navController.navigate("details_screen/$Name/$Address")})
            }
        }
    }
}


@Composable
fun Cellules(contact: Contact,onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }){
        AsyncImage(
            model="https://avatars.githubusercontent.com/u/583231?v=4",
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(20.dp))
        Column {
            Text(text=contact.nom,
                fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(text=contact.adress,
                modifier = Modifier.alpha(0.5f))
        }
    }
}


@Composable
fun DetailsScreen(name:String,address: String,navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Button(onClick = {navController.popBackStack()}) {
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
            Text(
                text=name,
                fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            Text(text=address,
                fontSize = 20.sp)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {
    }
}

data class Contact(val nom: String, val adress: String)

fun getContactList(): List<Contact> {
    val nomsPrenoms = listOf(
        "Alice Martin", "Bob Dupont", "Claire Bernard", "David Moreau", "Emma Lefevre",
        "Farid Garcia", "Georges Petit", "Hugo Durand", "Isabelle Roux", "Jean Faure",
        "Laura Noël", "Nicolas Perrin", "Sophie Lemoine", "Lucie Marchand", "Antoine Guerin",
        "Camille Boucher", "Théo Chevalier", "Julie Robert", "Paul Blanchard", "Manon Adam",
        "Léa Morin", "Maxime Aubry", "Chloé Giraud", "Yanis Fontaine", "Nina Fabre",
        "Lucas Millet", "Élodie Collet", "Jules Renaud", "Inès Leclerc", "Axel Laporte",
        "Mathilde Philippe", "Victor Georges", "Noémie Garnier", "Thomas Lebrun", "Sarah Lambert",
        "Adrien Bonnet", "Anaïs Meunier", "Quentin Lefèvre", "Marine Marty", "Romain Chauvet",
        "Caroline Tessier", "Émile Gaillard", "Justine Loiseau", "Enzo Pires", "Mélanie Rolland",
        "Raphaël Perrot", "Océane Breton", "Bastien Descamps", "Eva Gauthier", "Clément Masson"
    )

    val adresses = listOf(
        "42 rue de la Paix, Paris", "18 avenue Victor Hugo, Lyon", "77 boulevard Haussmann, Marseille",
        "12 rue des Lilas, Toulouse", "95 rue du Moulin, Nice", "63 rue de la République, Nantes",
        "21 allée des Acacias, Strasbourg", "34 chemin des Écoliers, Lille", "89 rue des Fleurs, Montpellier",
        "58 place de la Liberté, Bordeaux", "14 rue Jean Jaurès, Rennes", "73 avenue des Champs-Élysées, Paris",
        "37 rue du Faubourg, Dijon", "66 rue Pasteur, Grenoble", "25 impasse des Cerisiers, Clermont-Ferrand",
        "47 rue Victor Hugo, Tours", "30 rue Nationale, Le Havre", "51 avenue Jean Moulin, Saint-Étienne",
        "80 boulevard Carnot, Amiens", "96 rue du Général de Gaulle, Metz", "11 rue du Stade, Reims",
        "67 rue des Rosiers, Perpignan", "59 avenue de la Gare, Orléans", "83 rue de Strasbourg, Besançon",
        "28 rue des Tilleuls, Caen", "36 rue Gambetta, Pau", "92 rue de Verdun, Nîmes", "40 avenue de Provence, Angers",
        "16 boulevard du Nord, Limoges", "75 rue Léon Blum, Rouen", "88 rue Sainte-Catherine, Nancy",
        "33 rue du Marché, Mulhouse", "64 rue Saint-Martin, Valenciennes", "49 rue des Chênes, Troyes",
        "71 boulevard de la Gare, Bayonne", "27 avenue des Alpes, Annecy", "53 rue du Lac, La Rochelle",
        "38 avenue Maréchal Foch, Avignon", "15 rue des Peupliers, Chartres", "84 rue du Vieux-Port, Toulon",
        "29 rue des Bleuets, Poitiers", "70 boulevard Gambetta, Albi", "43 rue Montaigne, Tarbes",
        "60 rue de la Fontaine, Agen", "22 avenue du Parc, Brive-la-Gaillarde", "90 rue des Écoles, Colmar",
        "31 rue des Vosges, Épinal", "24 avenue de Bretagne, Quimper", "55 rue de Savoie, Vannes",
        "19 rue des Arts, Béziers"
    )

    return nomsPrenoms.zip(adresses).map { (nom, adresse) ->
        Contact(nom = nom, adress = adresse)
    }
}
