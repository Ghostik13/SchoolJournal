package com.example.schooljournal.scheduleCreateView

import android.app.Application
import androidx.lifecycle.*
import com.example.schooljournal.Navigation
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.data.SubjectRepositoryImpl
import com.example.schooljournal.dayFragments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleCreateViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: SubjectRepositoryImpl
    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun insertDays(days: MutableList<Day>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDays(days)
        }
    }

    fun insertSubjects(subjects: MutableList<Subject>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubjects(subjects)
        }
    }

    fun updateSubjects(subjects: List<Subject>, day: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubjects(subjects, day)
        }
    }
}