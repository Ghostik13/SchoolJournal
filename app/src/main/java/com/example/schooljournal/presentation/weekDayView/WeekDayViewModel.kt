package com.example.schooljournal.presentation.weekDayView

import android.app.Application
import androidx.lifecycle.*
import com.example.schooljournal.*

class WeekDayViewModel(application: Application) : AndroidViewModel(application) {

    private fun addSubjectsToList(subjectList: MutableList<String>, listOfEdits: List<String>) {
        for (i in subjectList.indices) {
            subjectList[i] = listOfEdits[i]
        }
    }

    fun fillOutAllLists(nameOfDay: String, listOfEdits: List<String>) {
        when (nameOfDay) {
            "пн" -> addSubjectsToList(mon, listOfEdits)
            "вт" -> addSubjectsToList(tue, listOfEdits)
            "ср" -> addSubjectsToList(wed, listOfEdits)
            "чт" -> addSubjectsToList(thu, listOfEdits)
            "пт" -> addSubjectsToList(fri, listOfEdits)
            "сб" -> addSubjectsToList(sat, listOfEdits)
            "вс" -> addSubjectsToList(sun, listOfEdits)
        }
    }

}