package com.example.navdrawer.service

// import com.example.navdrawer.model.OrgRegister
//COMENTE UN IMPORT QUE NO ME DEJABA COMPILAR
/*import com.example.navdrawer.model.OrgRegister */
import com.example.navdrawer.model.AddFavoriteOrganization
import com.example.navdrawer.model.AddFavoriteOrganizationResponse
import com.example.navdrawer.model.GetFavoriteOrganizationResponse
import com.example.navdrawer.model.PostRegister
import com.example.navdrawer.model.PostRegisterResponse
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    companion object {

        val instance: UserService =
            Retrofit.Builder().baseUrl("https://androidapi-8n8l-dev.fl0.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
    }


    @POST("user/register") // Ruta para el registro de usuario
    suspend fun insertUser(@Body user: UserRegister): UserRegistrationResponse

    @POST("user/login") // Ruta para el inicio de sesión de usuario
    suspend fun loginUser(@Body user: UserLogin): UserLoginResponse

    @GET("user") // Ruta para obtener todos los usuarios
    suspend fun getAllUsers(): List<UserRegister>

    @GET("user/{phone}") // Ruta para obtener un usuario específico
    suspend fun getUserByPhone(@Path("phone") phone: String): UserRegister

    @GET("user/{id}") // Ruta para obtener el id del usuario
    suspend fun getUserId(@Path("id") id: String): UserRegistrationResponse

    @GET("/organizations/tags/all") // Ruta para obtener todos los tags
    suspend fun getAllTags(): List<String>

    @POST("user/favorite")
    suspend fun insertFavorite(@Body favorite: AddFavoriteOrganization): AddFavoriteOrganizationResponse


    /*
    @GET("/organizations/tags") // Ruta para obtener organizaciones por tags
    suspend fun getOrgByTag(tag: List<Strng>): List<OrgRegister>
    */

    //Checar con métodos de nuestra API
    @POST("add-favorite/{organizationId}")
    suspend fun addFavoriteOrganization(
        @Header("Authorization") token: String,
        @Path("organizationId") organizationId: String
    ): AddFavoriteOrganizationResponse


    @GET("getUserFavoriteOrganizations/")
    suspend fun getUserFavoriteOrganization(
        @Header("Authorization") token: String
    ): GetFavoriteOrganizationResponse

    //Esos son todos los métodos para agregar a favoritos

    @PATCH("user/{phone}") // Ruta para actualizar un usuario específico
    suspend fun updateUser(@Path("phone") phone: Int, @Body user: UserRegister): UserRegister

    @DELETE("user/{phone}") // Ruta para eliminar un usuario específico
    suspend fun deleteUser(@Path("phone") phone: Int): Response<Void>

    //@GET("user/favorites/{userId}")
    //suspend fun getAllFavorites(@Path("userId") userId:String): List<String>
}
