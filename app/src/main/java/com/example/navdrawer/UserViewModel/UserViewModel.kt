package com.example.navdrawer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navdrawer.model.AddFavoriteOrganization
import com.example.navdrawer.model.AddFavoriteOrganizationResponse
import com.example.navdrawer.model.GetFavoriteOrganizationResponse
import com.example.navdrawer.model.GetAllFavorites
import com.example.navdrawer.model.PostsGetAll
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserProtectedResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(private val userService: UserService) : ViewModel() {


    private val _registrationResult = MutableStateFlow<UserRegistrationResponse?>(null)
    val registrationResult: StateFlow<UserRegistrationResponse?> = _registrationResult


    private val _loginResult = MutableStateFlow<UserLoginResponse?>(null)
    val loginResult: StateFlow<UserLoginResponse?> = _loginResult


    private val _protectedResult = MutableStateFlow<UserProtectedResponse?>(null)
    val protectedResult: StateFlow<UserProtectedResponse?> = _protectedResult


    //Aquí agregué algo para lo de favoritos
    private val _addOrgFavoriteResult = MutableStateFlow<AddFavoriteOrganizationResponse?>(null)
    val addOrgFavoriteResult: StateFlow<AddFavoriteOrganizationResponse?> = _addOrgFavoriteResult

    private val _getUserFavoriteOrgsResult =
        MutableStateFlow<GetFavoriteOrganizationResponse?>(null)
    val getUserFavoriteOrgsResult: StateFlow<GetFavoriteOrganizationResponse?> =
        _getUserFavoriteOrgsResult


    private val _getFavorite = MutableStateFlow<GetAllFavorites?>(null)
    val getFavorite: StateFlow<GetAllFavorites?> = _getFavorite

    fun addUser(
        name: String,
        lastname: String,
        phone: String,
        password: String,
        description: String
    ) {
        _registrationResult.value = null

        //val user = UserRegister(name,lastname,phone,email,password,tags,favorites)
        val user = UserRegister(name, lastname, phone, password, description)
        viewModelScope.launch {
            val response: UserRegistrationResponse
            try {
                response = userService.insertUser(user)
                _registrationResult.value = response
            } catch (e: Exception) {

                val errorResponse = UserRegistrationResponse(e.localizedMessage)
                _registrationResult.value = errorResponse
            }
        }
    }


    /*fun getFavorite(){
        viewModelScope.launch {
            val response: GetAllFavorites
            try {
                response = userService.getAllFavorite()
                _getFavorite.value = response
            } catch (e: Exception) {

                    val errorResponse = e.localizedMessage
                    Log.d("ERROR-API", errorResponse)
                    //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

     */

    /*
    fun getUserFavoriteOrganization(token: String) {
        viewModelScope.launch {
            val response: GetFavoriteOrganizationResponse
            try {
                response = userService.getUserFavoriteOrganization(token)
                _getUserFavoriteOrgsResult.value = response
            } catch (e: Exception) {

                val errorResponse = e.localizedMessage
                Log.d("ERROR-API", errorResponse)
                //_getUserFavoriteOrgsResult.value = errorResponse
            }
        }
    }

   fun addUserFavoriteOrganization(userId:String, orgId: String) {
        val favOrg = AddFavoriteOrganization(userId, orgId)
        viewModelScope.launch {
            val response: AddFavoriteOrganizationResponse
            try {
                response = userService.insertFavorite(favOrg)
                _addOrgFavoriteResult.value = response
            } catch (e: Exception) {
                val errorResponse = AddFavoriteOrganizationResponse(e.localizedMessage)
                _addOrgFavoriteResult.value = errorResponse
            }
        }
    }

 */

    fun addUserFavoriteOrganization(userId: String, orgId: String) {
        viewModelScope.launch {
            val response: AddFavoriteOrganizationResponse
            try {
                val favorite = AddFavoriteOrganization(userId, orgId)
                response = userService.insertFavorite(favorite)
                _addOrgFavoriteResult.value = response
            } catch (e: Exception) {
                val errorResponse = AddFavoriteOrganizationResponse(e.localizedMessage)
                _addOrgFavoriteResult.value = errorResponse
            }
        }
    }

    //Aquí acaba lo que agregué para favoritos

    fun loginUser(phone: String, password: String) {

        // Reset the login result to nul before making a new login request
        _loginResult.value = null

        val user = UserLogin(phone, password)

        viewModelScope.launch {
            val response: UserLoginResponse

            try {
                response = userService.loginUser(user)
                _loginResult.value = response
                Log.d("RESPONSE", response.id.toString())
            } catch (e: HttpException) {

                when (e.code()) {

                    400 -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = "Credenciales incorrectas"
                        val errorResponse = UserLoginResponse()
                        _loginResult.value = errorResponse
                    }

                    else -> {
                        Log.d("RESPONSE", e.localizedMessage)
                        val errorMessage = e.localizedMessage
                        val errorResponse = UserLoginResponse()
                        _loginResult.value = errorResponse
                    }
                }
            } catch (e: Exception) {
                Log.d("RESPONSE", e.localizedMessage)
                val errorMessage = e.localizedMessage
                val errorResponse = UserLoginResponse()
                _loginResult.value = errorResponse
            }

        }
    }
}



