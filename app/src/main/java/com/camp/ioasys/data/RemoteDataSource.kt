package com.camp.ioasys.data

import android.util.Log
import com.camp.ioasys.data.network.EnterpriseApi
import com.camp.ioasys.models.User
import okhttp3.Headers
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val enterpriseApi: EnterpriseApi
) {

    suspend fun signIn(email: String, password: String): Response<User> {
        val res = enterpriseApi.signIn(email, password)
        Log.i("Data", res.toString())
        return res
    }

}