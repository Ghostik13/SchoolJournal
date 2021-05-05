package com.example.schooljournal.weekDayView

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooljournal.*
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.Subject
import com.example.schooljournal.scheduleCreateView.ScheduleCreateFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDayViewModel(private val dayDao: DayDao) : ViewModel() {

    private lateinit var currentDayId: List<Int>
    private val _subjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    val subjectList: LiveData<List<Subject>> = _subjectList


    fun loadSubjects(day: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sList = dayDao
                .loadSubjectsForCurrentDay(Parser(day).parsingName)
                .subjects
            withContext(Dispatchers.Main) {
                _subjectList.value = sList
            }
        }
    }

    private val subjects: MutableList<Subject> = mutableListOf()

    fun insertSubjects(
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

    fun nextDay(day: String, navigation: Navigation, parser: Parser) {
        if (day == "Суббота") {
            navigation.initSchedule(ScheduleCreateFragment())
        } else {
            navigation.initSchedule(dayFragments[parser.currentIndex])
        }
    }
}