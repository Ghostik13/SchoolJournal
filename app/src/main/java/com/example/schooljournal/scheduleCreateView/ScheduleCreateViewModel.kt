package com.example.schooljournal.scheduleCreateView

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.data.SubjectRepositoryImpl
import kotlinx.coroutines.*

class ScheduleCreateViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: SubjectRepositoryImpl

    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun insertDays(days: MutableList<Day>) {
        runBlocking(Dispatchers.IO) { repository.insertDays(days) }
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
}