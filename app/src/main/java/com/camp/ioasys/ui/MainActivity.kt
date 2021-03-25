package com.camp.ioasys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.ioasys.R
import com.camp.ioasys.adapters.CompaniesAdapter
import com.camp.ioasys.databinding.ActivityMainBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mAdapter by lazy { CompaniesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra("access-token")
        val client = intent.getStringExtra("client")
        val uid = intent.getStringExtra("uid")

        setupRecycler()

        requestCompanies(accessToken!!, client!!, uid!!)

        mainViewModel.companies.observe(this, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
                    res.data?.let { mAdapter.setData(it) }
                } else -> {
                    Log.i("Data", res.message.toString())
                }
            }
        })

    }

    private fun setupRecycler() {
        binding.homeCompaniesRecycler.adapter = mAdapter
        binding.homeCompaniesRecycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
    }

    private fun requestCompanies(accessToken: String, client: String, uid: String) {
        mainViewModel.loadCompanies(accessToken, client, uid)
    }
}