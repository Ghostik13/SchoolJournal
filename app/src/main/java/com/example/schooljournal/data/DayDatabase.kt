package com.example.schooljournal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Day::class, Subject::class, Note::class], version = 1)
@TypeConverters(ToFromSubjectConverter::class)
abstract class DayDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao

    companion object {

        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context): DayDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DayDatabase::class.java, "Day.db"
            )
                .build()
    }
}