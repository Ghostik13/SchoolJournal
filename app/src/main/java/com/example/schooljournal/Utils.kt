package com.example.schooljournal

import android.content.Context
import android.widget.Toast
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

fun Context.toast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}