package com.example.schooljournal.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.schooljournal.presentation.weekDayView.WeekDaysFragment

val dayFragments = listOf(
    WeekDaysFragment.newInstance("Понедельник"),
    WeekDaysFragment.newInstance("Вторник"),
    WeekDaysFragment.newInstance("Среда"),
    WeekDaysFragment.newInstance("Четверг"),
    WeekDaysFragment.newInstance("Пятница"),
    WeekDaysFragment.newInstance("Суббота"),
    WeekDaysFragment.newInstance("Воскресенье")
)

private const val emptySubject = ""
private const val size = 7

val mon = MutableList(size) {emptySubject}
val tue = MutableList(size) {emptySubject}
val wed = MutableList(size) {emptySubject}
val thu = MutableList(size) {emptySubject}
val fri = MutableList(size) {emptySubject}
val sat = MutableList(size) {emptySubject}
val sun = MutableList(size) {emptySubject}

const val DAY_NAME = "DAY_NAME"
const val WEEK_ID = "WEEK_ID"

fun Context.toast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

var fragments = arrayListOf<Fragment>()