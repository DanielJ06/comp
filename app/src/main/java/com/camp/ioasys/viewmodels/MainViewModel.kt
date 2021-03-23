package com.camp.ioasys.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camp.ioasys.data.Repository
import com.camp.ioasys.models.User
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

    fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            val response = repository.remote.signIn(email, password)
            handleLogin(response)
        } catch (e: Exception) {}
    }

    private fun handleLogin(response: Response<User>): NetworkResult<Headers>? {
        return when {
            response.isSuccessful -> {
                val headers = response.headers()
                NetworkResult.Success(headers)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

}