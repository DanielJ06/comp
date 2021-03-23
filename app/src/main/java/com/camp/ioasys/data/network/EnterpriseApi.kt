package com.camp.ioasys.data.network

import com.camp.ioasys.models.User
import retrofit2.Response
import retrofit2.http.POST

interface EnterpriseApi {

    @POST("/users/auth/sign_in")
    suspend fun signIn(email: String, password: String): Response<User>

}