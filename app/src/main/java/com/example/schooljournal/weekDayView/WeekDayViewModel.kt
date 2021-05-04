package com.example.schooljournal.weekDayView

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.schooljournal.*
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.Subject
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDayViewModel(private val dayDao: DayDao) : ViewModel() {

    private var flag = 0

    private lateinit var currentDayId: List<Int>
    private val subjects: MutableList<Subject> = mutableListOf()

    fun insertSubjects(
        dayDao: DayDao,
        dayOfWeek: String,
        activity: Activity,
        context: Context,
        subj1: String,
        subj2: String,
        subj3: String,
        subj4: String,
        subj5: String,
        subj6: String,
        subj7: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            //список айдишников нужного дня недели
            currentDayId = dayDao.getId(dayOfWeek)
            withContext(Dispatchers.Main) {
                //цикл для добавления всех предметов из edit_text в список subjs
                for (i in currentDayId.indices) {
                    addSubjectToList(i, subj1)
                    addSubjectToList(i, subj2)
                    addSubjectToList(i, subj3)
                    addSubjectToList(i, subj4)
                    addSubjectToList(i, subj5)
                    addSubjectToList(i, subj6)
                    addSubjectToList(i, subj7)
                }
            }
            //вставляем список subjects в subject_table
            dayDao.insertSubjects(subjects)
            //цикл для добавления предметов из subject_table в day_table по dayId
            for (i in currentDayId.indices) {
                val list = dayDao.getCurrentSubjects(currentDayId[i])
                dayDao.updateDay(list, currentDayId[i])
            }
        }
        activity.toast(context, "Subjects added")
    }

    private fun addSubjectToList(i: Int, nameOfSubject: String) {
        if (nameOfSubject.isNotEmpty()) {
            val subject1 = Subject(0, currentDayId[i], nameOfSubject, " ", " ", " ")
            subjects.add(subject1)
        }
    }

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

    fun nextDay(day: String, navigation: Navigation, parser: Parser) {
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