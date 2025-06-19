package com.example.androidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.data.ContactRepository
import com.example.androidtest.model.ApiContact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ContactRepository) : ViewModel() {

    private val _contact = MutableStateFlow<ApiContact?>(null)
    val contact: StateFlow<ApiContact?> = _contact

    fun loadContactById(id: Int) {
        viewModelScope.launch {
            try {
                _contact.value = repository.getContactById(id)
            } catch (e: Exception) {
                _contact.value = null
            }
        }
    }
}
