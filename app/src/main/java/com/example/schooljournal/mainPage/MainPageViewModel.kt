package com.example.schooljournal.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.SubjectRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPageViewModel(application: Application): AndroidViewModel(application) {

    private val repository: SubjectRepositoryImpl
    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun getDays(weekId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _days.postValue(repository.getDays(weekId))
        }
    }

    private val _days: MutableLiveData<List<Day>> = MutableLiveData()
    val days: LiveData<List<Day>> = _days
    val subjectsMon = repository.subjectsMon
    val subjectsTue = repository.subjectsTue
    val subjectsWed = repository.subjectsWed
    val subjectsThu = repository.subjectsThu
    val subjectsFri = repository.subjectsFri
    val subjectsSat = repository.subjectsSat
}