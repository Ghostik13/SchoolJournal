package com.example.schooljournal.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.schooljournal.R
import com.example.schooljournal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (firstRunFinished()) {
            startActivity(Intent(this, NavigationActivity::class.java))
        } else initFragment()
    }

    private fun firstRunFinished(): Boolean {
        val sharedPreferences = getSharedPreferences("firstRun", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Done", false)
    }

    private fun initFragment() {
        replaceFragment(IntroFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun initNavigation(fragment: Fragment) {
        replaceFragment(fragment)
    }

    override fun initPrevious() {
        onBackPressed()
    }
}