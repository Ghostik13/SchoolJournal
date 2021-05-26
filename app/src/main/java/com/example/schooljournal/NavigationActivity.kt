package com.example.schooljournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.schooljournal.mainPage.MainPageNavigation
import com.example.schooljournal.mainPage.ViewPagerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_navigation.*

private lateinit var bottomNavigation: BottomNavigationView

class NavigationActivity : AppCompatActivity(), MainPageNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        bottomNavigation = bnv
        val navController = findNavController(R.id.fragment)
        bottomNavigation.setupWithNavController(navController)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }

    override fun nextWeek(fragment: Fragment) {
        replaceFragment(fragment)
    }

    override fun previousWeek(fragment: Fragment) {
        replaceFragment(fragment)
    }
}