package com.camp.ioasys.data.network

import retrofit2.http.POST

interface EnterpriseApi {

    @POST("/users/auth/sign_in")
    suspend fun signIn(email: String, password: String)

}