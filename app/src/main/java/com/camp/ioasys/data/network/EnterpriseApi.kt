package com.camp.ioasys.data.network

import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.User
import retrofit2.Response
import retrofit2.http.*

interface EnterpriseApi {

    @FormUrlEncoded
    @POST("users/auth/sign_in")
    suspend fun signIn(
        @Field("email") email: String, @Field("password") password: String
    ): Response<Any>

    @GET("enterprises")
    suspend fun getCompanies(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String
    ): Response<CompaniesResponse>

}