package com.example.schooljournal

import com.example.schooljournal.weekDayView.WeekDaysFragment

val dayFragments = listOf(
    WeekDaysFragment.newInstance("Понедельник"),
    WeekDaysFragment.newInstance("Вторник"),
    WeekDaysFragment.newInstance("Среда"),
    WeekDaysFragment.newInstance("Четверг"),
    WeekDaysFragment.newInstance("Пятница"),
    WeekDaysFragment.newInstance("Суббота"),
    WeekDaysFragment.newInstance("Воскресенье")
)

const val DAY_NAME = "DAY_NAME"