package com.example.schooljournal.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.schooljournal.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainPageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SubjectRepositoryImpl

    init {
        val dao = DayDatabase.getInstance(application).dayDao()
        repository = SubjectRepositoryImpl(dao)
    }

    fun getDays(weekId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val days = repository.getDays(weekId)
            withContext(Dispatchers.Main) {
                _days.value = days
            }
        }
    }

    private val _days: MutableLiveData<List<Day>> = MutableLiveData()
    val days: LiveData<List<Day>> = _days

    fun getNote(weekId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = repository.getNote(weekId)
            withContext(Dispatchers.Main) {
                _note.value = note
            }
        }
    }

    private val _note: MutableLiveData<Note> = MutableLiveData()
    val note: LiveData<Note> = _note

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    suspend fun getCurrentSubjects(id: Int): List<Subject> {
        return repository.getCurrentSubjects(id)
    }

    suspend fun getHomework(currentId: Int): String {
        return repository.getHomework(currentId)
    }

    fun updateHomework(subject: Subject) {
       runBlocking(Dispatchers.IO) {
            repository.updateSubject(subject)
        }
    }
}