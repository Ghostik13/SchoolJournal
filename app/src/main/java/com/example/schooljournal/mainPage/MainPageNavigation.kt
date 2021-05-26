package com.example.schooljournal.mainPage

import androidx.fragment.app.Fragment

interface MainPageNavigation {
    fun nextWeek(fragment: Fragment)
    fun previousWeek(fragment: Fragment)
}