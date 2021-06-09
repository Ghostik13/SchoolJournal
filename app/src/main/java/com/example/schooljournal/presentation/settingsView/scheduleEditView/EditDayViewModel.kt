package com.example.schooljournal.presentation.settingsView.scheduleEditView

import androidx.lifecycle.*
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.domain.SubjectRepository
import kotlinx.coroutines.*

class EditDayViewModel(private val repository: SubjectRepository) : ViewModel() {

    fun getSubjectsForCurrentDay(dayOfWeek: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val subjectNames = repository.getSubjectsForCurrentDay(dayOfWeek)
            withContext(Dispatchers.Main) {
                _subjectNames.value = subjectNames
            }
        }

    }

    private val _subjectNames: MutableLiveData<List<String>> = MutableLiveData()
    val subjectNames: LiveData<List<String>> = _subjectNames

    private fun getSubjectsForCurrentDays(
        dayOfWeek: Int,
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
        day: Int
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
        day: Int
    ) {
        list.observeForever {
            if (it.isEmpty() && newName.isNotEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val ids = repository.getIdsForDay(day)
                    ids.forEach { dayId ->
                        val subject =
                            Subject(0, dayId, newName, "", day, "")
                        withContext(Dispatchers.Main) {
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
        day: Int
    ) {
        firstSubjectList.value = checkIfNameOk(name1, day)
        secondSubjectList.value = checkIfNameOk(name2, day)
        thirdSubjectList.value = checkIfNameOk(name3, day)
        fourthSubjectList.value = checkIfNameOk(name4, day)
        fifthSubjectList.value = checkIfNameOk(name5, day)
        sixSubjectList.value = checkIfNameOk(name6, day)
        seventhSubjectList.value = checkIfNameOk(name7, day)
    }

    private fun checkIfNameOk(name: String, day: Int): List<Subject> {
        var list = emptyList<Subject>()
        if (name.isNotEmpty()) {
            val job = viewModelScope.launch(Dispatchers.IO) {
                list = getSubjectsForCurrentDays(day, name)
            }
            runBlocking { job.join() }
        }
        return list
    }
}