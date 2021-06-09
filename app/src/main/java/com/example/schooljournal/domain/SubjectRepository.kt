package com.example.schooljournal.domain

import androidx.lifecycle.LiveData
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject

interface SubjectRepository {
    suspend fun insertDays(days: MutableList<Day>)
    suspend fun insertSubjects(subjects: MutableList<Subject>)
    suspend fun insertOneSubject(subject: Subject)
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun updateSubject(subject: Subject)
    fun getDays(weekId: Int): List<Day>
    fun getIdsForDay(dayOfWeek: Int): List<Int>
    fun getCurrentSubjects(id: Int): List<Subject>
    fun getHomework(currentId: Int): String
    fun getNote(weekId: Int): Note
    fun getSubjectsForCurrentDay(dayOfWeek: Int): List<String>
    fun getSubjectsForCurrentDays(dayOfWeek: Int, nameOfSubject: String): List<Subject>
}