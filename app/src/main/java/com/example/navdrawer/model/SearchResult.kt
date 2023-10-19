package com.example.navdrawer.model

data class SearchResult(
    val selectedTags: List<String>,
    val organizations: List<OrganizationResponseItem>
)
