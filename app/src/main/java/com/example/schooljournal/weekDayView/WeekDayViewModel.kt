package com.example.schooljournal.weekDayView

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.schooljournal.*
import com.example.schooljournal.data.DayDao
import kotlinx.android.synthetic.main.fragment_week_days.*
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDayViewModel(private val dayDao: DayDao): ViewModel() {

    private var flag = 0

    fun loadSubjects(day: String, et: EditText) {
        GlobalScope.launch(Dispatchers.IO) {
            val subjectList = dayDao
                .loadSubjectsForCurrentDay(Parser(day).parsingName)
                .subjects
            withContext(Dispatchers.Main) {
                if (subjectList.isNotEmpty()) {
                    et.setText(subjectList[0].name)
                }
            }
        }
    }

    fun nextDay(day: String, navigation: Navigation){
        val parser = Parser(day)
        if (day == "Суббота") {
            navigation.initSchedule(ScheduleCreateFragment())
        } else {
            navigation.initSchedule(dayFragments[parser.currentIndex])
        }
    }

    fun addSubjectField(
        context: Context,
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        et6: EditText,
        et7: EditText
    ) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        when (flag) {
            0 -> {
                etActiveOn(inputMethodManager, et1, 0)
                flag = 1
            }
            1 -> {
                etActiveOn(inputMethodManager, et2, 1)
                flag = 2
            }
            2 -> {
                etActiveOn(inputMethodManager, et3, 2)
                flag = 3
            }
            3 -> {
                etActiveOn(inputMethodManager, et4, 3)
                flag = 4
            }
            4 -> {
                etActiveOn(inputMethodManager, et5, 5)
                flag = 5
            }
            5 -> {
                etActiveOn(inputMethodManager, et6, 6)
                flag = 6
            }
            6 -> {
                etActiveOn(inputMethodManager, et7, 7)
            }
        }
    }

    private fun etActiveOn(inputMethodManager: InputMethodManager, et: EditText, flag: Int) {
        et.visibility = View.VISIBLE
        et.requestFocus()
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, flag)
    }

}