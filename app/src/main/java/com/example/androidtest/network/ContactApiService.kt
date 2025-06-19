package com.example.androidtest.network

import com.example.androidtest.model.ApiContact
import retrofit2.http.GET

interface ContactApiService{
    @GET("users")
    suspend fun getContacts():List<ApiContact>
}