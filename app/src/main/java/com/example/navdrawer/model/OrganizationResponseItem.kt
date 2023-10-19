package com.example.navdrawer.model

data class OrganizationResponseItem(
    val __v: Int,
    val _id: String,
    val city: String,
    val description: String,
    val email: String,
    val image: String,
    val linkFacebook: String,
    val linkInstagram: String,
    val linkOther: String,
    val linkTwitter: String,
    val linkWeb: String,
    val name: String,
    val password: String,
    val phone: String,
    val schedule: String,
    val state: String,
    val street: String,
    val suburb: String,
    val tags: List<String>
)