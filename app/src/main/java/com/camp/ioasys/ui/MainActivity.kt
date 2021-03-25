package com.camp.ioasys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.camp.ioasys.R
import com.camp.ioasys.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra("access-token")
        val client = intent.getStringExtra("client")
        val uid = intent.getStringExtra("uid")

        Log.i("DataMainActivity", "$accessToken - $client - $uid")
    }
}