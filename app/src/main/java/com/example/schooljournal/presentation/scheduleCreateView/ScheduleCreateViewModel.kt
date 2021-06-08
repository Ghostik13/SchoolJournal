package com.example.schooljournal.presentation.scheduleCreateView

import java.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.*
import com.example.schooljournal.*
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.domain.SubjectRepository
import kotlinx.coroutines.*
import java.util.*

class ScheduleCreateViewModel(private val repository: SubjectRepository) : ViewModel() {

    fun insertAllData() {
        insertDates()
        insertDays()
        runBlocking(Dispatchers.IO) { repository.insertDays(allData) }
        insertSubjects(1, mon)
        insertSubjects(2, tue)
        insertSubjects(3, wed)
        insertSubjects(4, thu)
        insertSubjects(5, fri)
        insertSubjects(6, sat)
        insertSubjects(7, sun)
    }

    private fun insertSubjects(subjects: MutableList<Subject>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubjects(subjects)
        }
    }

    private fun insertSubjects(dayOfWeek: Int, subjectList: MutableList<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val ids = repository.getIdsForDay(dayOfWeek)
            withContext(Dispatchers.Main) {
                val subjects = mutableListOf<Subject>()
                subjectList.forEach { nameOfSubject ->
                    if (nameOfSubject.isNotEmpty()) {
                        ids.forEach {
                            val subject =
                                Subject(0, it, nameOfSubject, "", dayOfWeek, "")
                            subjects.add(subject)
                        }
                    }
                }
                insertSubjects(subjects)
            }
        }
    }

    private val allData = mutableListOf<Day>()
    private val days: MutableList<String> = mutableListOf()
    private val subjects = emptyList<Subject>()

    private fun insertDates() {
        val dateFormat = SimpleDateFormat("yyyyMMdd-u", Locale.getDefault())
        var calendar = Calendar.getInstance()
        for (i in 0..364) {
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val day = dateFormat.format(calendar.time)
            days.add(day)
            calendar = Calendar.getInstance()
        }
        var dayOk = true
        while (dayOk) {
            if (days[0].substring(9, 10).toInt() != 1) {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val day = dateFormat.format(calendar.time)
                days.add(0, day)
            } else dayOk = false
        }
    }

    private fun insertDays() {
        var week = 1
        for (i in 0 until days.size) {
            val dayName = days[i].substring(9, 10).toInt()
            val dayDate = days[i].substring(0, 8).toInt()
            if (i in (0..6)) {
                week = 1
            } else if (dayName == 1) week++
            val day =
                Day(0, week, dayDate, dayName, subjects)
            allData.add(day)
        }
        Log.d("DATA", allData.size.toString())
    }
}