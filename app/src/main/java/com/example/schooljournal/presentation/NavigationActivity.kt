package com.example.schooljournal.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.schooljournal.R
import com.example.schooljournal.databinding.ActivityNavigationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity(){

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigation = binding.bnv
        val navController = findNavController(R.id.fragment)
        bottomNavigation.setupWithNavController(navController)
    }
}

