package com.camp.ioasys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        binding.loginSubmitButton.setOnClickListener {
            onSubmit("testeapple@ioasys.com.br", "1234123")
        }

        setContentView(binding.root)
    }

    private fun onSubmit(email: String, password: String) {
        mainViewModel.signIn(email, password)
        mainViewModel.userHeaders.observe(this, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, res.toString(), Toast.LENGTH_LONG).show()
                } else -> {
                    binding.loginEmailInputLayout.error = " "
                    binding.loginPasswordInputLayout.error = " "
                    Toast.makeText(this, res.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}