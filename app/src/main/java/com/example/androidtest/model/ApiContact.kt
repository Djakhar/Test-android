package com.example.androidtest.model

data class ApiContact(
    val id:Int,
    val name: String,
    val address:Address
)

data class Address(
    val street:String,
    val suite:String,
    val city:String,
    val zipcode:String
)