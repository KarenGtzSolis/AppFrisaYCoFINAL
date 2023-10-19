package com.example.navdrawer.OrganizationViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.FindTags
import com.example.navdrawer.model.GetAllFavorites
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.model.PostsGetAll
import com.example.navdrawer.model.SearchResult
import com.example.navdrawer.model.TagsList
import com.example.navdrawer.service.OrgService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrganizationViewModel(private val orgService: OrgService) : ViewModel() {

    private val _getAllFavorites = MutableStateFlow<GetAllFavorites?>(null)
    val getAllFavorites: StateFlow<GetAllFavorites?> = _getAllFavorites

    private val _getOrgResult = MutableStateFlow<OrganizationResponse?>(null)
    val getOrgResult: StateFlow<OrganizationResponse?> = _getOrgResult

    private val _getAllPosts = MutableStateFlow<PostsGetAll?>(null)
    val getAllPosts: StateFlow<PostsGetAll?> = _getAllPosts

    private val _getAllTags = MutableStateFlow<TagsList?>(null)
    val getAllTags: StateFlow<TagsList?> = _getAllTags


    private val _findbytags = MutableStateFlow<TagsList?>(null)
    val findbytags: StateFlow<TagsList?> = _findbytags

    private val _findOrganizationsByTags = MutableStateFlow<TagsList?>(null)
    val findOrganizationsByTags: StateFlow<TagsList?> = _findOrganizationsByTags

    private val _searchResult = mutableStateOf<SearchResult?>(null)
    val searchResult: State<SearchResult?> = _searchResult

    fun getUserFavoriteOrganization() {
        viewModelScope.launch {
            val response: OrganizationResponse

            try {
                response = orgService.getAllOrgs()
                _getOrgResult.value = response
            } catch (e: Exception) {

                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

    fun getAllFavorites(userId:String){
        viewModelScope.launch {
            val response: GetAllFavorites

            try {
                response = orgService.getAllFavorites(userId)
                _getAllFavorites.value = response
            } catch (e: Exception) {

                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

    fun getAllPosts(){
        viewModelScope.launch {
            val response: PostsGetAll

            try {
                response = orgService.getAllPosts()
                _getAllPosts.value = response
            } catch (e: Exception) {

                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

    fun getAllTags(){
        viewModelScope.launch {
            val response: TagsList

            try {
                response = orgService.getAllTags()
                _getAllTags.value = response
            } catch (e: Exception) {

                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

    fun findbytags(tags: List<String>) {
        viewModelScope.launch {
            try {
                Log.d("LISTA2", tags.toString())
                val tagList = FindTags(tags)
                Log.d("LISTA3", tagList.toString())

                val response = orgService.findbytags(tagList)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Log the response body if it's not null
                        Log.d("API Response", responseBody.toString())

                        // Create a SearchResult object to store selected tags and response data
                        val searchResult = SearchResult(tags, responseBody)

                        _searchResult.value = searchResult
                    } else {
                        // Log a message if the response body is null
                        Log.d("API Response", "Response body is null")
                    }
                } else {
                    // Log the error message if the API call is not successful
                    Log.d("API Response", "API call failed with status code: ${response.code()}")
                }
            } catch (e: Exception) {
                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
            }
        }
    }





    fun findOrganizationsByTags(selectedTags: List<String>) {
        viewModelScope.launch {
            try {
                val response = orgService.findbytags(selectedTags)
                _findbytags.value = TagsList()
            } catch (e: Exception) {
                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                // Maneja el error de alguna manera si es necesario
            }
        }
    }



}