package com.example.navdrawer.model

data class OrgRegister(
    val name: String?="",
    val phone: String?="",
    val email: String?="",
    val street: String?="",
    val suburb: String?="",
    val city: String?="",
    val state: String?="",
    val schedule: String?="",
    val linkWeb: String?="",
    val linkFacebook: String?="",
    val linkInstagram: String?="",
    val linkTwitter: String?="",
    val linkOther: String?="",
    val description: String?="",
    val image: String?="",
    val tags: List<String> = emptyList(),
    val password: String?=""
)
