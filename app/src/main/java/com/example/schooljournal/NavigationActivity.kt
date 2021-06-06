package com.example.schooljournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity(){

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        bottomNavigation = bnv
        val navController = findNavController(R.id.fragment)
        bottomNavigation.setupWithNavController(navController)
    }
}

