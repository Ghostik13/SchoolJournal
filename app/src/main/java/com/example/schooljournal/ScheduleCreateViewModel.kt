package com.example.schooljournal

import androidx.lifecycle.ViewModel

class ScheduleCreateViewModel : ViewModel() {

    fun initMonday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[0])
    }
    fun initTuesday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[1])
    }
    fun initWednesday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[2])
    }
    fun initThursday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[3])
    }
    fun initFriday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[4])
    }
    fun initSaturday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[5])
    }
    fun initSunday(navigation: Navigation) {
        navigation.initSchedule(dayFragments[6])
    }
}