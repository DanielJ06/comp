package com.camp.ioasys.data

import com.camp.ioasys.data.network.EnterpriseApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val enterpriseApi: EnterpriseApi
) {

    suspend fun signIn(email: String, password: String) {
        return enterpriseApi.signIn(email, password)
    }

}