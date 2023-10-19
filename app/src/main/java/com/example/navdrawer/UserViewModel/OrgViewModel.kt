package com.example.navdrawer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.OrgLogin
import com.example.navdrawer.model.OrgLoginResponse
import com.example.navdrawer.model.OrgRegister
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.model.PostRegister
import com.example.navdrawer.model.PostRegisterResponse
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.OrgService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrgViewModel(private val orgService: OrgService) : ViewModel() {

    private val _orgRegistrationResult = MutableStateFlow<OrgRegisterResponse?>(null)
    val orgRegisterResult: StateFlow<OrgRegisterResponse?> = _orgRegistrationResult

    private val _orgLoginResult = MutableStateFlow<OrgLoginResponse?>(null)
    val orgLoginResult: StateFlow<OrgLoginResponse?> = _orgLoginResult

    private val _postRegistrationResult = MutableStateFlow<PostRegisterResponse?>(null)
    val postRegisterResult: StateFlow<PostRegisterResponse?> = _postRegistrationResult


    private val _orgProtectedResult = MutableStateFlow<OrgRegisterResponse?>(null)
    val orgProtectedResult: StateFlow<OrgRegisterResponse?> = _orgProtectedResult


    fun addOrg(name: String, phone: String, email:String,street:String,suburb:String,city:String,state:String,
                schedule:String, linkWeb:String, linkFacebook:String, linkInstagram:String,linkTwitter:String, linkOther:String,
                description: String, image:String, tags: List<String>, password:String) {

        _orgRegistrationResult.value = null

        //val user = UserRegister(name,lastname,phone,email,password,tags,favorites)
        val org = OrgRegister(name, phone, email, street,suburb,city,state,schedule,
            linkWeb,linkFacebook,linkInstagram,linkTwitter,linkOther,description,image,tags,password)
        viewModelScope.launch {
            val response: OrgRegisterResponse
            try {
                response = orgService.insertOrg(org)
                _orgRegistrationResult.value = response
            } catch (e: Exception) {

                val errorResponse = OrgRegisterResponse(e.localizedMessage)
                _orgRegistrationResult.value = errorResponse
            }
        }
    }

    fun loginOrg(phone: String, password: String) {

        // Reset the login result to nul before making a new login request
        _orgLoginResult.value = null

        val org = OrgLogin(phone, password)

        viewModelScope.launch {
            val response: OrgLoginResponse

            try {
                response = orgService.loginOrg(org)
                _orgLoginResult.value = response
                Log.d("RESPONSE", response.id.toString())
            } catch (e: HttpException) {

                when (e.code()) {

                    500 -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = "Credenciales incorrectas"
                        val errorResponse = OrgLoginResponse()
                        _orgLoginResult.value = errorResponse
                    }

                    else -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = e.localizedMessage
                        val errorResponse = OrgLoginResponse()
                        _orgLoginResult.value = errorResponse
                    }
                }
            } catch (e: Exception) {
                Log.d("RESPONSE", e.localizedMessage)
                val errorMessage = e.localizedMessage
                val errorResponse = OrgLoginResponse()
                _orgLoginResult.value = errorResponse
            }

        }
    }

    fun addPost(title:String, content:String, image:String) {

        _postRegistrationResult.value = null

        //val user = UserRegister(name,lastname,phone,email,password,tags,favorites)
        val post = PostRegister(title, content, image)
        viewModelScope.launch {
            val response: PostRegisterResponse
            try {
                response = orgService.insertPost(post)
                _postRegistrationResult.value = response
            } catch (e: Exception) {

                val errorResponse = PostRegisterResponse(e.localizedMessage)
                _postRegistrationResult.value = errorResponse
            }
        }
    }


}
