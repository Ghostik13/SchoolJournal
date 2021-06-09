package com.example.schooljournal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.schooljournal.data.converters.ToFromSubjectConverter
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.data.model.Subject

@Database(entities = [Day::class, Subject::class, Note::class], version = 1)
@TypeConverters(ToFromSubjectConverter::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDao: DayDao
}