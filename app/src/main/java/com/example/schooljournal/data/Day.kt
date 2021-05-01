package com.example.schooljournal.data

import androidx.room.*

@Entity(tableName = "day_table")
data class Day(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val date: Int,
    val dayOfTheWeek: String,
    @TypeConverters(ToFromSubjectConverter::class)
    val subjects: List<Subject>
)

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dayId: Int,
    val name: String,
    val teacher: String,
    val mark: String,
    val homework: String
)

@Entity(tableName = "week_table")
data class Week(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: List<Day>,
    val note: Note
)

@Entity(tableName = "week_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val note: String
)

data class Sub(
    @ColumnInfo(name = "subjects")
    val subjects: List<Subject>
)