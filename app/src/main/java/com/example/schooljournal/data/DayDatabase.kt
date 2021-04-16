package com.example.schooljournal.data

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
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        main()
                        kk()
                        ioThread {
                            getInstance(context).dayDao().insertData(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()

        val PREPOPULATE_DATA = mutableListOf<Day>()
        var days: MutableList<String> = mutableListOf()
        private val subjects = emptyList<Subject>()
        private val subj: List<Subject> = listOf(
            Subject(0, 0, "English", "Smb", " ", "smth..."),
            Subject(0, 0, "Maths", "Smb", " ", "smth..."),
            Subject(0, 0, "History", "Smb", " ", "smth..."),
            Subject(0, 0, "Physics", "Smb", " ", "smth..."),
            Subject(0, 0, "Chemistry", "Smb", " ", "smth...")
        )


        fun main() {
            val dateFormat = SimpleDateFormat("yyyyMMdd-EE")
            var calendar = Calendar.getInstance()
            for (i in 0..365) {
                calendar.add(Calendar.DAY_OF_YEAR, i)
                val day = dateFormat.format(calendar.time)
                days.add(day)
                calendar = Calendar.getInstance()
            }
        }

        fun kk() {
            for (i in 0 until days.size) {
                val day =
                    Day(0, 1, days[i].substring(0, 8).toInt(), days[i].substring(9, 11), subjects)
                PREPOPULATE_DATA.add(day)
            }
        }
    }
}