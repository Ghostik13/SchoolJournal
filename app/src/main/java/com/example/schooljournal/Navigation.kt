package com.example.schooljournal

import androidx.fragment.app.Fragment

interface Navigation {
    fun initSchedule(fragment: Fragment)
    fun initPrevious()
}