package com.camp.ioasys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.camp.ioasys.R
import com.camp.ioasys.databinding.ActivityLoginBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        mainViewModel.signIn("testeapple@ioasys.com.br", "12341234")
        mainViewModel.userHeaders.observe(this, Observer { res ->
            if (res.isSuccessful) {
                Log.i("Data", res.body().toString())
            } else {
                Log.i("Data", "erro")
            }
        })

        setContentView(binding.root)
    }

    private fun onSubmit(email: String, password: String) {
        Log.i("Data", "Bateu onSubmit")
        mainViewModel.signIn(email, password)

    }
}