package com.example.schooljournal

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.schooljournal.data.Subject
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

val mon = mutableListOf("",  "", "", "", "", "", "")
val tue = mutableListOf("",  "", "", "", "", "", "")
val wed = mutableListOf("",  "", "", "", "", "", "")
val thu = mutableListOf("",  "", "", "", "", "", "")
val fri = mutableListOf("",  "", "", "", "", "", "")
val sat = mutableListOf("",  "", "", "", "", "", "")
val sun = mutableListOf("",  "", "", "", "", "", "")

const val DAY_NAME = "DAY_NAME"
const val WEEK_ID = "WEEK_ID"

fun Context.toast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

var liveSubjectsMon = emptyList<Subject>()
var liveSubjectsTue = emptyList<Subject>()
var liveSubjectsWed = emptyList<Subject>()
var liveSubjectsThu = emptyList<Subject>()
var liveSubjectsFri = emptyList<Subject>()
var liveSubjectsSat = emptyList<Subject>()

var fragments = arrayListOf<Fragment>()