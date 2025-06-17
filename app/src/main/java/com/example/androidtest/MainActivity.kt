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
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactList()
        }
    }
}

@Composable
fun ContactList() {
    var listContact=listOf<Contact>()
    listContact=getContactList()
    LazyColumn {
        items(listContact){contact->
            Cellules(contact)
        }
    }
}


@Composable
fun Cellules(contact: Contact) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)){
        AsyncImage(
            model="https://avatar.iran.liara.run/public/${(1..100).random()}",
            contentDescription = "Avatar",
            modifier = Modifier.width(64.dp).height(64.dp)
        )
        Spacer(Modifier.width(20.dp))
        Column {
            Text(text=contact.nom)
            Spacer(Modifier.height(4.dp))
            Text(text=contact.adress)
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
