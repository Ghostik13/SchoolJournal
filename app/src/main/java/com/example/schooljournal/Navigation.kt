package com.example.schooljournal

import androidx.fragment.app.Fragment

interface Navigation {
    fun initNavigation(fragment: Fragment)
    fun initPrevious()
}