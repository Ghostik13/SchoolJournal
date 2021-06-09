package com.example.rest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rest.R
import com.example.rest.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
    }
}