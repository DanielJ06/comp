package com.camp.ioasys.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.camp.ioasys.R
import com.camp.ioasys.databinding.ActivityLoginBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // testeapple@ioasys.com.br
        // 12341234

        binding.loginSubmitButton.setOnClickListener {
            val email = binding.loginEmailEditText.text?.toString()
            val password = binding.loginPasswordEditText.text?.toString()

            if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                Snackbar.make(it, "Preencha os campos", Snackbar.LENGTH_SHORT).show()
            } else {
                onSubmit(email!!, password!!)
            }
        }

        mainViewModel.userHeaders.observe(this, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
                    binding.loadingProgressBar.visibility = View.INVISIBLE
                    binding.whiteLoadingEffect.visibility = View.INVISIBLE

                    val accessToken = res.data!!.get("access-token")
                    val client = res.data.get("client")
                    val uid = res.data.get("uid")

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("access-token", accessToken)
                    intent.putExtra("client", client)
                    intent.putExtra("uid", uid)

                    startActivity(intent)
                    finish()
                }
                is NetworkResult.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.whiteLoadingEffect.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.loginEmailInputLayout.error = " "
                    binding.loginPasswordInputLayout.error = " "

                    binding.loginErrorText.visibility = View.VISIBLE
                    binding.loginErrorText.text = res.message

                    binding.loadingProgressBar.visibility = View.INVISIBLE
                    binding.whiteLoadingEffect.visibility = View.INVISIBLE
                }
            }
        })

    }

    private fun onSubmit(email: String, password: String) {
        mainViewModel.signIn(email, password)
    }
}