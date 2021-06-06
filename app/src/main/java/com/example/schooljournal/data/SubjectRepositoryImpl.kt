package com.example.schooljournal.data

import androidx.lifecycle.LiveData
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

    override suspend fun updateDays(subject: List<Subject>, id: Int) {
        dayDao.updateDay(subject, id)
    }

    override suspend fun getDays(weekId: Int): List<Day> {
        return dayDao.getDays(weekId)
    }

    override suspend fun getIdsForDay(dayOfWeek: String): List<Int> {
        return dayDao.getIdsForDay(dayOfWeek)
    }

    override suspend fun getCurrentSubjects(id: Int): List<Subject> {
        return dayDao.getCurrentSubjects(id)
    }

    override suspend fun getHomework(currentId: Int): String {
        return dayDao.getHomework(currentId)
    }

    override suspend fun updateSubject(subject: Subject) {
        dayDao.updateHomework(subject)
    }

    override suspend fun getNote(weekId: Int): Note {
        return dayDao.getNote(weekId)
    }

    override suspend fun insertNote(note: Note) {
        dayDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dayDao.updateNote(note)
    }

    override suspend fun getSubjectsForCurrentDay(dayOfWeek: String): List<String> {
        return dayDao.getSubjectsForCurrentDay(dayOfWeek)
    }

    override suspend fun getIdsForCurrentSubjectAndDay(
        dayOfWeek: String,
        nameOfSubject: String
    ): List<Int> {
        return  dayDao.getIdsForCurrentSubjectAndDay(dayOfWeek, nameOfSubject)
    }

    override suspend fun getDayIdsForCurrentSubjectAndDay(dayOfWeek: String, nameOfSubject: String): List<Int> {
        return dayDao.getDayIdsForCurrentSubjectAndDay(dayOfWeek, nameOfSubject)
    }

    override suspend fun getSubjectsForCurrentDays(
        dayOfWeek: String,
        nameOfSubject: String
    ): List<Subject> {
        return dayDao.getSubjectsForCurrentDays(dayOfWeek, nameOfSubject)
    }
}