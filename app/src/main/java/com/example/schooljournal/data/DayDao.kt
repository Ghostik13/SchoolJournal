package com.example.schooljournal.data

import androidx.room.*

@Dao
interface DayDao {

    @Insert
    fun insertData(data: List<Day>)

    @Insert
    fun insertSubjects(subjects: List<Subject>)           //вставляем предметы в Subjects_table

    @Insert
    fun insertOneSubject(subject: Subject)

    @Update
    suspend fun updateSubjects(subjects: List<Subject>)

    @Query("UPDATE day_table SET subjects = :subject WHERE id = :ids")    //обновляем сабджекты в таблице day_table
    fun updateDay(subject: List<Subject>, ids: Int)

    @Query("SELECT id FROM day_table WHERE dayOfTheWeek = :dayOfWeek")    //получаем список айдишников для поля dayOfTheWeek из day_table
    fun getId(dayOfWeek: String): List<Int>

    @Query("SELECT * FROM day_table")
    fun getDays(): List<Day>

    @Query("SELECT * FROM subject_table")
    fun getSubjects(): List<Subject>

    @Query("SELECT * FROM subject_table WHERE dayId = :id")      //получаем список предметов для айди конкретного дня
    fun getCurrentSubjects(id: Int): List<Subject>

    @Query("SELECT subjects FROM day_table WHERE dayOfTheWeek = :day LIMIT 1")      //получаем список предметов для дня недели
    fun loadSubjectsForCurrentDay(day: String): Sub

    @Query("SELECT id FROM subject_table WHERE dayId = :daiId")
    fun getIdsForCurrentSubject(daiId: Int): List<Int>

}