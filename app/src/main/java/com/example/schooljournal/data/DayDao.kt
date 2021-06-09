package com.example.schooljournal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject

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

    @Update
    suspend fun updateHomework(subject: Subject)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note_table WHERE weekId =:weekId")
    fun getNote(weekId: Int): Note

    @Query("SELECT homework FROM subject_table WHERE id = :currentId")
    fun getHomework(currentId: Int): String

    @Query("SELECT * FROM day_table WHERE weekId = :weekId")
    fun getDays(weekId: Int): List<Day>

    @Query("SELECT id FROM day_table WHERE dayOfTheWeek =:dayOfWeek")
    fun getIdsForDay(dayOfWeek: Int): List<Int>

    @Query("SELECT * FROM subject_table WHERE dayId = :id")
    fun getCurrentSubjects(id: Int): List<Subject>

    @Query("SELECT DISTINCT name FROM subject_table WHERE dayOfWeek = :dayOfWeek ")
    fun getSubjectsForCurrentDay(dayOfWeek: Int): List<String>

    @Query("SELECT * FROM subject_table WHERE dayOfWeek = :dayOfWeek AND name = :nameOfSubject")
    fun getSubjectsForCurrentDays(dayOfWeek: Int, nameOfSubject: String): List<Subject>

}