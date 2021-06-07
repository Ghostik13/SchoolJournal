package com.example.schooljournal.presentation.scheduleCreateView

import android.app.Application
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.*
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.data.SubjectRepositoryImpl
import kotlinx.coroutines.*
import java.util.*

class ScheduleCreateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SubjectRepositoryImpl

    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun insertAllData() {
        runBlocking(Dispatchers.IO) { repository.insertDays(allData) }
    }

    private fun insertSubjects(subjects: MutableList<Subject>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubjects(subjects)
        }
    }

    private fun updateDays(subjects: List<Subject>, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDays(subjects, id)
        }
    }

    fun insertSubjects(dayOfWeek: String, subjectList: MutableList<String>) {
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
            for (i in ids) {
                val currentSubjects =
                    runBlocking(Dispatchers.Main) { repository.getCurrentSubjects(i) }
                updateDays(currentSubjects, i)
            }
        }
    }

    private val allData = mutableListOf<Day>()
    private val days: MutableList<String> = mutableListOf()
    private val subjects = emptyList<Subject>()

    fun insertDates() {
        val dateFormat = SimpleDateFormat("yyyyMMdd-EE", Locale.getDefault())
        var calendar = Calendar.getInstance()
        for (i in 0..364) {
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val day = dateFormat.format(calendar.time)
            days.add(day)
            calendar = Calendar.getInstance()
        }
        var dayOk = true
        while (dayOk) {
            if (days[0].substring(9, 11) != "пн") {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val day = dateFormat.format(calendar.time)
                days.add(0, day)
            } else dayOk = false
        }
    }

    fun insertDays() {
        var week = 1
        for (i in 0 until days.size) {
            val dayName = days[i].substring(9, 11)
            val dayDate = days[i].substring(0, 8).toInt()
            if (i in (0..6)) {
                week = 1
            } else if (dayName == "пн") week++
            val day =
                Day(0, week, dayDate, dayName, subjects)
            allData.add(day)
        }
    }
}