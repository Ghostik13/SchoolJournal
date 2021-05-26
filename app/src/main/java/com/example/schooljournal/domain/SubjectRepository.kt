package com.example.schooljournal.domain

import androidx.lifecycle.LiveData
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.Subject

interface SubjectRepository {
    suspend fun insertDays(days: MutableList<Day>)
    suspend fun insertSubjects(subjects: MutableList<Subject>)
    suspend fun updateSubjects(subject: List<Subject>, dayOfWeek: String)
    suspend fun getDays(weekId: Int): List<Day>
}