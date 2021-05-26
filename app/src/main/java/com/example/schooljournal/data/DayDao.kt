package com.example.schooljournal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DayDao {

    @Insert
    suspend fun insertSubjects(subjects: List<Subject>)

    @Insert
    suspend fun insertDays(days: MutableList<Day>)

    @Query("SELECT * FROM day_table WHERE subjects NOT LIKE  '[]'")
    fun getWorkDays(): LiveData<List<Day>>

    @Query("SELECT * FROM day_table WHERE weekId = :weekId")
    suspend fun getDays(weekId: Int): List<Day>

    @Query("UPDATE day_table SET subjects = :subject WHERE dayOfTheWeek = :dayOfWeek")    //обновляем сабджекты в таблице day_table
    fun updateDay(subject: List<Subject>, dayOfWeek: String)

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = :dayOfWeek")

    fun getSubjects(dayOfWeek: String): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'пн'")
    fun getSubjectsForMonday(): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'вт'")
    fun getSubjectsForTuesday(): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'ср'")
    fun getSubjectsForWednesday(): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'чт'")
    fun getSubjectsForThursday(): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'пт'")
    fun getSubjectsForFriday(): LiveData<List<Subject>>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = 'сб'")
    fun getSubjectsForSaturday(): LiveData<List<Subject>>

}