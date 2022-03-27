package com.example.gamegivewayapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamegivewayapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}