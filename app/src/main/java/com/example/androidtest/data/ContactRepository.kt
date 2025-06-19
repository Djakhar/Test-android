package com.example.androidtest.data

import com.example.androidtest.model.Contact
import com.example.androidtest.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository {
    suspend fun getContactList(): List<Contact> = withContext(Dispatchers.IO) {
        try {
            val apiContacts = RetrofitInstance.api.getContacts()
            val mapped = apiContacts.map {
                Contact(
                    nom = it.name,
                    adress = "${it.address.street}, ${it.address.city}"
                )
            }
            List(5) { mapped }.flatten()//permet d'avoir une liste de 50 contact car l'api n'en donne que 10
        } catch (e: Exception) {
            emptyList()
        }
    }
}
