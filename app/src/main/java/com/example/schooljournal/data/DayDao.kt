package com.example.schooljournal.data

import androidx.room.*

@Dao
interface DayDao {

    @Insert
    fun insertData(data: List<Day>)

    @Insert
    fun insertSubjects(subjects: List<Subject>)

    @Insert
    fun insertOneSubject(subject: Subject)

    @Query("UPDATE day_table SET subjects = :subject WHERE id = :ids")
    fun updateDay(subject: List<Subject>, ids: Int)

    @Query("SELECT id FROM day_table WHERE dayOfTheWeek = :dayOfWeek")
    fun getId(dayOfWeek: String): List<Int>

    @Query("SELECT * FROM day_table")
    fun getData(): List<Day>

    @Query("SELECT * FROM subject_table")
    fun getSubjects(): List<Subject>

    @Query("SELECT * FROM subject_table WHERE dayId = :id")
    fun getCurrentSubjects(id: Int): List<Subject>

}