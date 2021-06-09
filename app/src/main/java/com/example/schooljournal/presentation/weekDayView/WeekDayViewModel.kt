package com.example.schooljournal.presentation.weekDayView

import androidx.lifecycle.*
import com.example.schooljournal.utils.*

class WeekDayViewModel : ViewModel() {

    private fun addSubjectsToList(subjectList: MutableList<String>, listOfEdits: List<String>) {
        for (i in subjectList.indices) {
            subjectList[i] = listOfEdits[i]
        }
    }

    fun fillOutAllLists(nameOfDay: Int, listOfEdits: List<String>) {
        when (nameOfDay) {
            1 -> addSubjectsToList(mon, listOfEdits)
            2 -> addSubjectsToList(tue, listOfEdits)
            3 -> addSubjectsToList(wed, listOfEdits)
            4 -> addSubjectsToList(thu, listOfEdits)
            5 -> addSubjectsToList(fri, listOfEdits)
            6 -> addSubjectsToList(sat, listOfEdits)
            7 -> addSubjectsToList(sun, listOfEdits)
        }
    }

}