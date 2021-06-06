package com.example.schooljournal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DayDao {

    @Insert
    suspend fun insertSubjects(subjects: List<Subject>)

    @Insert
    suspend fun insertOneSubject(subject: Subject)

    @Insert
    suspend fun insertDays(days: MutableList<Day>)

    @Insert
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM note_table WHERE weekId =:weekId")
    fun getNote(weekId: Int): Note

    @Query("SELECT * FROM day_table WHERE subjects NOT LIKE  '[]'")
    fun getWorkDays(): LiveData<List<Day>>

    @Query("SELECT homework FROM subject_table WHERE id = :currentId")
    fun getHomework(currentId: Int): String

    @Update
    fun updateHomework(subject: Subject)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM day_table WHERE weekId = :weekId")
    suspend fun getDays(weekId: Int): List<Day>

    @Query("SELECT id FROM day_table WHERE dayOfTheWeek =:dayOfWeek")
    suspend fun getIdsForDay(dayOfWeek: String): List<Int>

    @Query("UPDATE day_table SET subjects = :subject WHERE id = :id")    //обновляем сабджекты в таблице day_table
    fun updateDay(subject: List<Subject>, id: Int)

    @Query("SELECT * FROM subject_table WHERE dayId = :id")      //получаем список предметов для айди конкретного дня
    suspend fun getCurrentSubjects(id: Int): List<Subject>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = :dayOfWeek")
    fun getSubjects(dayOfWeek: String): LiveData<List<Subject>>

    @Query("SELECT DISTINCT name FROM subject_table WHERE dayOfWeek = :dayOfWeek ")
    fun getSubjectsForCurrentDay(dayOfWeek: String): List<String>

    @Query("SELECT id FROM subject_table WHERE dayOfWeek = :dayOfWeek AND name = :nameOfSubject")
    fun getIdsForCurrentSubjectAndDay(dayOfWeek: String, nameOfSubject: String): List<Int>

    @Query("SELECT dayId FROM subject_table WHERE dayOfWeek = :dayOfWeek AND name = :nameOfSubject")
    fun getDayIdsForCurrentSubjectAndDay(dayOfWeek: String, nameOfSubject: String): List<Int>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = :dayOfWeek AND name = :nameOfSubject")
    fun getSubjectsForCurrentDays(dayOfWeek: String, nameOfSubject: String): List<Subject>

}