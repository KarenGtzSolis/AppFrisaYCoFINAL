package com.example.navdrawer.model

data class UserRegister(
    val name: String ="", // Puede ser nulo
    val lastname: String="", // Puede ser nulo
    val phone: String, // Puede ser nulo
    //val email: String?="", // Puede ser nulo
    val password: String?="", // Puede ser nulo
    val description: String?=""
    //val tags: List<String> = emptyList(), // Puede ser una lista vacía
    //val favorites: List<String> = emptyList() // Puede ser una lista vacía
)
