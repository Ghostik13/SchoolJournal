package com.example.schooljournal.data

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

@Database(entities = [Day::class, Subject::class], version = 1)
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
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        autoInsertData()
                        ioThread {
                            getInstance(context).dayDao().insertData(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()

        private fun autoInsertData() {
            insertDates()
            insertDays()
        }

        val PREPOPULATE_DATA = mutableListOf<Day>()
        private val days: MutableList<String> = mutableListOf()
        private val subjects = emptyList<Subject>()

        @SuppressLint("SimpleDateFormat")
        private fun insertDates() {
            val dateFormat = SimpleDateFormat("yyyyMMdd-EE")
            var calendar = Calendar.getInstance()
            for (i in 0..365) {
                calendar.add(Calendar.DAY_OF_YEAR, i)
                val day = dateFormat.format(calendar.time)
                days.add(day)
                calendar = Calendar.getInstance()
            }
        }

        private fun insertDays() {
            for (i in 0 until days.size) {
                val day =
                    Day(0, 1, days[i].substring(0, 8).toInt(), days[i].substring(9, 11), subjects)
                PREPOPULATE_DATA.add(day)
            }
        }
    }
}