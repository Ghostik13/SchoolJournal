package com.example.schooljournal.presentation.mainPage

import androidx.lifecycle.*
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.domain.SubjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainPageViewModel(private val repository: SubjectRepository) : ViewModel() {

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

    fun getCurrentSubjects(id: Int): List<Subject> {
        return repository.getCurrentSubjects(id)
    }

    fun getHomework(currentId: Int): String {
        return repository.getHomework(currentId)
    }

    fun updateHomework(subject: Subject) {
        runBlocking(Dispatchers.IO) {
            repository.updateSubject(subject)
        }
    }
}