package com.camp.ioasys.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.camp.ioasys.databinding.FragmentLoginBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginSubmitButton.setOnClickListener {
            val email = binding.loginEmailEditText.text?.toString()
            val password = binding.loginPasswordEditText.text?.toString()

            if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                Snackbar.make(it, "Preencha os campos", Snackbar.LENGTH_SHORT).show()
            } else {
                onSubmit(email!!, password!!)
            }
        }

        mainViewModel.userHeaders.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
                    binding.loadingProgressBar.visibility = View.INVISIBLE
                    binding.whiteLoadingEffect.visibility = View.INVISIBLE
                    val accessToken = res.data!!.get("access-token")
                    val client = res.data.get("client")
                    val uid = res.data.get("uid")

                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                            accessToken,
                            client,
                            uid
                        )
                    )
                    mainViewModel.userHeaders.postValue(null)
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

        return binding.root
    }

    private fun onSubmit(email: String, password: String) {
        mainViewModel.signIn(email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}