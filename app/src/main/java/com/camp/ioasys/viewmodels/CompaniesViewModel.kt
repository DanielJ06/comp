package com.camp.ioasys.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.camp.ioasys.data.Repository
import com.camp.ioasys.data.database.CompaniesEntity
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM */
    val readCompanies: LiveData<List<CompaniesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertCompanies(companiesEntity: CompaniesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCompanies(companiesEntity)
        }

    /** RETROFIT */
    val companies: MutableLiveData<NetworkResult<CompaniesResponse>> = MutableLiveData()

    fun loadCompanies(accessToken: String, client: String, uid: String, query: String?) = viewModelScope.launch {
        companies.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCompanies(accessToken, client, uid, query)
                companies.value = handleCompanies(response)

                val companies = companies.value!!.data
                if (companies != null) {
                    offlineCatchCompanies(companies)
                }
            } catch (e: Exception) {
                companies.value = NetworkResult.Error("Companies not found")
            }
        } else {
            companies.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCatchCompanies(companies: CompaniesResponse) {
        val companiesEntity = CompaniesEntity(companies)
        insertCompanies(companiesEntity)
    }

    private fun handleCompanies(response: Response<CompaniesResponse>): NetworkResult<CompaniesResponse> {
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

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}