package com.camp.ioasys.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camp.ioasys.data.Repository
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val userHeaders: MutableLiveData<NetworkResult<Headers>> = MutableLiveData()
    val companies: MutableLiveData<NetworkResult<CompaniesResponse>> = MutableLiveData()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            val response = repository.remote.signIn(email, password)
            userHeaders.value = handleLogin(response)
        } catch (e: Exception) {}
    }

    private fun handleLogin(response: Response<Any>): NetworkResult<Headers> {
        userHeaders.value = NetworkResult.Loading()
        return when {
            response.isSuccessful -> {
                val headers = response.headers()
                NetworkResult.Success(headers)
            }
            response.code() == 401 -> {
                NetworkResult.Error("Invalid login credentials. Please try again.")
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    fun loadCompanies(accessToken: String, client: String, uid: String) = viewModelScope.launch {
        try {
            val response = repository.remote.getCompanies(accessToken, client, uid)
            companies.value = handleCompanies(response)
        } catch (e: Exception) {}
    }

    private fun handleCompanies(response: Response<CompaniesResponse>): NetworkResult<CompaniesResponse> {
        companies.value = NetworkResult.Loading()
        return when {
            response.isSuccessful -> {
                val data = response.body()
                NetworkResult.Success(data!!)
            }
            response.code() == 401 -> {
                NetworkResult.Error("You need to sign in before continuing.")
            } else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

}