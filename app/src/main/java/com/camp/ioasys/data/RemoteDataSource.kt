package com.camp.ioasys.data

import android.util.Log
import com.camp.ioasys.data.network.EnterpriseApi
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.User
import okhttp3.Headers
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val enterpriseApi: EnterpriseApi
) {

    suspend fun signIn(email: String, password: String): Response<Any> {
        return enterpriseApi.signIn(email, password)
    }

    suspend fun getCompanies(
        accessToken: String,
        client: String,
        uid: String
    ): Response<CompaniesResponse> {
        return enterpriseApi.getCompanies(accessToken, client, uid)
    }
}