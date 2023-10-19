package com.example.navdrawer.model

data class UserLoginResponse (
    val token: String? ="",
    val id: String = "",
    val name: String = "",
    val lastname: String = "",
    val phone: String = "",
    val tags: List<String> = emptyList(),
    val favorites: List<String> = emptyList()
)
