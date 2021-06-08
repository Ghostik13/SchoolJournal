package com.example.schooljournal.domain

import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject

interface SubjectRepository {
    suspend fun insertDays(days: MutableList<Day>)
    suspend fun insertSubjects(subjects: MutableList<Subject>)
    suspend fun insertOneSubject(subject: Subject)
    suspend fun getDays(weekId: Int): List<Day>
    suspend fun getIdsForDay(dayOfWeek: Int): List<Int>
    suspend fun getCurrentSubjects(id: Int): List<Subject>
    suspend fun getHomework(currentId: Int): String
    suspend fun updateSubject(subject: Subject)
    suspend fun getNote(weekId: Int): Note
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getSubjectsForCurrentDay(dayOfWeek: Int): List<String>
    suspend fun getSubjectsForCurrentDays(dayOfWeek: Int, nameOfSubject: String): List<Subject>
}