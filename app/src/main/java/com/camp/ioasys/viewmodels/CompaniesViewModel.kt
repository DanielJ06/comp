package com.camp.ioasys.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camp.ioasys.data.Repository
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val companies: MutableLiveData<NetworkResult<CompaniesResponse>> = MutableLiveData()

    fun loadCompanies(accessToken: String, client: String, uid: String, query: String?) = viewModelScope.launch {
        try {
            val response = repository.remote.getCompanies(accessToken, client, uid, query)
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

    fun clearStatus() {
        companies.value = null
    }

}