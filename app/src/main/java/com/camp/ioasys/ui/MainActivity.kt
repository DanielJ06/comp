package com.camp.ioasys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camp.ioasys.R
import com.camp.ioasys.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra("access-token")
        val client = intent.getStringExtra("client")
        val uid = intent.getStringExtra("uid")

        if (!accessToken.isNullOrEmpty() && !client.isNullOrEmpty() && !uid.isNullOrEmpty()) {
            binding.accessToken.text = accessToken
            binding.client.text = client
            binding.uid.text = uid
        }
    }
}