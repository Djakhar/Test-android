package com.example.androidtest.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtest.data.ContactRepository
import com.example.androidtest.viewmodel.DetailsViewModel

class DetailsViewModelFactory(private val repository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel>create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel inconnu")
    }
}