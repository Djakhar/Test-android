package com.example.androidtest.network

import com.example.androidtest.model.ApiContact
import retrofit2.http.GET
import retrofit2.http.Path


interface ContactApiService{
    @GET("users")
    suspend fun getContacts():List<ApiContact>
    @GET("users/{id}")
    suspend fun getContactById(@Path("id")id:Int): ApiContact
}