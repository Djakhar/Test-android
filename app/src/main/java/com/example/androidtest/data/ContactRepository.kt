package com.example.androidtest.data

import com.example.androidtest.model.ApiContact
import com.example.androidtest.model.Contact
import com.example.androidtest.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ContactRepository {
    suspend fun getContactList(): List<Contact> = withContext(Dispatchers.IO) {
        try {
            val apiContacts = RetrofitInstance.api.getContacts()
            val listContact = apiContacts.map {
                Contact(
                    id = it.id,
                    nom = it.name,
                    adress = "${it.address.street}, ${it.address.city}"
                )
            }
            List(5) { listContact }.flatten()//permet d'avoir une liste de 50 contact car l'api n'en donne que 10
        } catch (_: Exception) {
            emptyList()
        }
    }
    suspend fun getContactById(id:Int): ApiContact{
        return RetrofitInstance.api.getContactById(id)
    }
}
