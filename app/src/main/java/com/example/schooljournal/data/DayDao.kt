package com.example.schooljournal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayDao {

    @Insert
    fun insertData(data: List<Day>)

    @Insert
    fun insertSubjects(data: List<Subject>)

    @Query("SELECT * FROM day_table")
    fun getData(): List<Day>

    @Query("SELECT * FROM subject_table")
    fun getSubjects(): List<Subject>

}