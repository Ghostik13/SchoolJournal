package com.example.schooljournal.domain

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.Note
import com.example.schooljournal.data.Subject

interface SubjectRepository {
    suspend fun insertDays(days: MutableList<Day>)
    suspend fun insertSubjects(subjects: MutableList<Subject>)
    suspend fun insertOneSubject(subject: Subject)
    suspend fun updateDays(subject: List<Subject>, id: Int)
    suspend fun getDays(weekId: Int): List<Day>
    suspend fun getIdsForDay(dayOfWeek: String): List<Int>
    suspend fun getCurrentSubjects(id: Int): List<Subject>
    suspend fun getHomework(currentId: Int): String
    suspend fun updateSubject(subject: Subject)
    suspend fun getNote(weekId: Int): Note
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun getSubjectsForCurrentDay(dayOfWeek: String): List<String>
    suspend fun getIdsForCurrentSubjectAndDay(dayOfWeek: String, nameOfSubject: String): List<Int>
    suspend fun getDayIdsForCurrentSubjectAndDay(dayOfWeek: String, nameOfSubject: String): List<Int>
    suspend fun getSubjectsForCurrentDays(dayOfWeek: String, nameOfSubject: String): List<Subject>
}