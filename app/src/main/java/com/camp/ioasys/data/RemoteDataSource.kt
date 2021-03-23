package com.camp.ioasys.data

import com.camp.ioasys.data.network.EnterpriseApi
import com.camp.ioasys.models.User
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val enterpriseApi: EnterpriseApi
) {

    suspend fun signIn(email: String, password: String): Response<User> {
        return enterpriseApi.signIn(email, password)
    }

}