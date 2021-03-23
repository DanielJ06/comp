package com.camp.ioasys.data.network

import com.camp.ioasys.models.User
import okhttp3.Headers
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EnterpriseApi {

    @FormUrlEncoded
    @POST("users/auth/sign_in")
    suspend fun signIn(
        @Field("email") email: String, @Field("email") password: String
    ): Response<User>

}