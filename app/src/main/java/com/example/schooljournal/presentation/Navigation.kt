package com.example.schooljournal.presentation

import androidx.fragment.app.Fragment

interface Navigation {
    fun initNavigation(fragment: Fragment)
    fun initPrevious()
}