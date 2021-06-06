package com.example.schooljournal.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.data.SubjectRepositoryImpl
import kotlinx.coroutines.*

class EditDayViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SubjectRepositoryImpl

    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun getSubjectsForCurrentDay(dayOfWeek: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val subjectNames = repository.getSubjectsForCurrentDay(dayOfWeek)
            withContext(Dispatchers.Main) {
                _subjectNames.value = subjectNames
            }
        }
    }

    private val _subjectNames: MutableLiveData<List<String>> = MutableLiveData()
    val subjectNames: LiveData<List<String>> = _subjectNames

    private suspend fun getSubjectsForCurrentDays(
        dayOfWeek: String,
        nameOfSubject: String
    ): List<Subject> {
        return repository.getSubjectsForCurrentDays(dayOfWeek, nameOfSubject)
    }

    fun updateSubjects(
        name1: String,
        name2: String,
        name3: String,
        name4: String,
        name5: String,
        name6: String,
        name7: String,
        day: String
    ) {
        observeAndUpdate(name1, firstSubjectList, day)
        observeAndUpdate(name2, secondSubjectList, day)
        observeAndUpdate(name3, thirdSubjectList, day)
        observeAndUpdate(name4, fourthSubjectList, day)
        observeAndUpdate(name5, fifthSubjectList, day)
        observeAndUpdate(name6, sixSubjectList, day)
        observeAndUpdate(name7, seventhSubjectList, day)
    }

    private fun observeAndUpdate(
        newName: String,
        list: MutableLiveData<List<Subject>>,
        day: String
    ) {
        list.observeForever {
            if (it.isEmpty() && newName.isNotEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val ids = repository.getIdsForDay(day)
                    withContext(Dispatchers.Main) {
                        ids.forEach { dayId ->
                            val subject =
                                Subject(0, dayId, newName, "", day, "")
                            insertOneSubject(subject)
                        }
                    }
                }
            } else {
                for (i in 0..it.lastIndex) {
                    val updatedSubject = Subject(
                        it[i].id,
                        it[i].dayId,
                        newName,
                        it[i].homework,
                        it[i].dayOfWeek,
                        it[i].photo
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.updateSubject(updatedSubject)
                    }
                }
            }
        }
    }

    private fun insertOneSubject(subject: Subject) {
        runBlocking(Dispatchers.IO) {
            repository.insertOneSubject(subject)
        }
    }

    private val firstSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val secondSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val thirdSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val fourthSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val fifthSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val sixSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()
    private val seventhSubjectList: MutableLiveData<List<Subject>> = MutableLiveData()

    fun fillLists(
        name1: String,
        name2: String,
        name3: String,
        name4: String,
        name5: String,
        name6: String,
        name7: String,
        day: String
    ) {
        firstSubjectList.value = checkIfNameOk(name1, day)
        secondSubjectList.value = checkIfNameOk(name2, day)
        thirdSubjectList.value = checkIfNameOk(name3, day)
        fourthSubjectList.value = checkIfNameOk(name4, day)
        fifthSubjectList.value = checkIfNameOk(name5, day)
        sixSubjectList.value = checkIfNameOk(name6, day)
        seventhSubjectList.value = checkIfNameOk(name7, day)
    }

    private fun checkIfNameOk(name: String, day: String): List<Subject> {
        var list = emptyList<Subject>()
        if (name.isNotEmpty()) {
            Log.d("textInVM", "ok")
            runBlocking(Dispatchers.IO) {
                list = getSubjectsForCurrentDays(
                    day,
                    name
                )
            }
        }
        return list
    }
}