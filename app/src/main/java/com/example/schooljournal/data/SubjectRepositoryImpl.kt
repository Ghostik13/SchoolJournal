package com.example.schooljournal.data

import androidx.lifecycle.LiveData
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.domain.SubjectRepository

class SubjectRepositoryImpl(private val dayDao: DayDao) : SubjectRepository {

    override suspend fun insertDays(days: MutableList<Day>) {
        dayDao.insertDays(days)
    }

    override suspend fun insertSubjects(subjects: MutableList<Subject>) {
        dayDao.insertSubjects(subjects)
    }

    override suspend fun insertOneSubject(subject: Subject) {
        dayDao.insertOneSubject(subject)
    }

    override fun getDays(weekId: Int): List<Day> {
        return dayDao.getDays(weekId)
    }

    override fun getIdsForDay(dayOfWeek: Int): List<Int> {
        return dayDao.getIdsForDay(dayOfWeek)
    }

    override fun getCurrentSubjects(id: Int): List<Subject> {
        return dayDao.getCurrentSubjects(id)
    }

    override fun getHomework(currentId: Int): String {
        return dayDao.getHomework(currentId)
    }

    override suspend fun updateSubject(subject: Subject) {
        dayDao.updateHomework(subject)
    }

    override fun getNote(weekId: Int): Note {
        return dayDao.getNote(weekId)
    }

    override suspend fun insertNote(note: Note) {
        dayDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dayDao.updateNote(note)
    }

    override fun getSubjectsForCurrentDay(dayOfWeek: Int): List<String> {
        return dayDao.getSubjectsForCurrentDay(dayOfWeek)
    }

    override fun getSubjectsForCurrentDays(
        dayOfWeek: Int,
        nameOfSubject: String
    ): List<Subject> {
        return dayDao.getSubjectsForCurrentDays(dayOfWeek, nameOfSubject)
    }
}