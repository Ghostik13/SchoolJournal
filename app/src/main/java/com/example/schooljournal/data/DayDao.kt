package com.example.schooljournal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayDao {

    @Insert
    fun insertData(data: List<Day>)

    @Query("SELECT * FROM day_table")
    fun getData(): List<Day>

}