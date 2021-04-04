package com.example.schooljournal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setDate()

}