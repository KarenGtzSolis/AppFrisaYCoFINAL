package com.example.navdrawer.service

import com.example.navdrawer.model.FindTags
import com.example.navdrawer.model.GetAllFavorites
import com.example.navdrawer.model.OrgLogin
import com.example.navdrawer.model.OrgLoginResponse
import com.example.navdrawer.model.OrgRegister
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.model.OrganizationResponse
import com.example.navdrawer.model.PostRegister
import com.example.navdrawer.model.PostRegisterResponse
import com.example.navdrawer.model.PostsGetAll
import com.example.navdrawer.model.TagsList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrgService {
    companion object {
        val instance: OrgService =
            Retrofit.Builder().baseUrl("https://androidapi-8n8l-dev.fl0.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OrgService::class.java)
    }

    @POST("organization/register") // Incluye la ruta específica del endpoint aquí
    suspend fun insertOrg(@Body org: OrgRegister): OrgRegisterResponse

    @POST("organization/login")
    suspend fun loginOrg(@Body user: OrgLogin): OrgLoginResponse

    @POST("organization/findbytags")
    suspend fun findbytags(@Body request: List<String>): OrganizationResponse

    @GET("organizations/all")
    suspend fun getAllOrgs(): OrganizationResponse

    @POST("posting")
    suspend fun insertPost(@Body post: PostRegister): PostRegisterResponse

    @GET("posts/all")
    suspend fun getAllPosts(): PostsGetAll

    @GET("organizations/tags/all")
    suspend fun getAllTags(): TagsList


    @GET("user/favorites/{userId}")
    suspend fun getAllFavorites(@Path("userId") userId:String): GetAllFavorites

    @POST("organizations/findTags")
    suspend fun findbytags(@Body request: FindTags): Response<OrganizationResponse>


}

