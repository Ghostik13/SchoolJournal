package com.example.schooljournal.weekDayView

import android.app.Application
import androidx.lifecycle.*
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.data.SubjectRepositoryImpl
import com.example.schooljournal.domain.SubjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeekDayViewModel(application: Application) : AndroidViewModel(application) {

    val subjectName = MutableLiveData<String>()

    val dao = DayDatabase.getInstance(application).dayDao()
    private val repository: SubjectRepositoryImpl = SubjectRepositoryImpl(dao)
//    private val _subjectList: LiveData<List<Subject>> = repository.loadSubjects("")

    private var day: String = ""

//    init {
//        val dao = DayDatabase.getInstance(application).dayDao()
//        repository = SubjectRepositoryImpl(dao)
//        _subjectList = repository.loadSubjects("пн")
//    }

//    val subjectList: LiveData<List<Subject>> = _subjectList

    fun getDay(dayOfWeek: String) {
        day = dayOfWeek
    }

}