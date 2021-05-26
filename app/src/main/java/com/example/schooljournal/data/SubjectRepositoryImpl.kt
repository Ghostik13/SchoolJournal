package com.example.schooljournal.data

import androidx.lifecycle.LiveData
import com.example.schooljournal.domain.SubjectRepository

class SubjectRepositoryImpl(private val dayDao: DayDao) : SubjectRepository {


    val subjectsMon = dayDao.getSubjectsForMonday()
    val subjectsTue = dayDao.getSubjectsForTuesday()
    val subjectsWed = dayDao.getSubjectsForWednesday()
    val subjectsThu = dayDao.getSubjectsForThursday()
    val subjectsFri = dayDao.getSubjectsForFriday()
    val subjectsSat = dayDao.getSubjectsForSaturday()

    override suspend fun insertDays(days: MutableList<Day>) {
        dayDao.insertDays(days)
    }

    override suspend fun insertSubjects(subjects: MutableList<Subject>) {
        dayDao.insertSubjects(subjects)
    }

    override suspend fun updateSubjects(subject: List<Subject>, dayOfWeek: String) {
        dayDao.updateDay(subject, dayOfWeek)
    }

    override suspend fun getDays(weekId: Int): List<Day> {
        return dayDao.getDays(weekId)
    }
}