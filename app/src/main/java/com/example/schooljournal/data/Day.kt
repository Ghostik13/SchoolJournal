package com.example.schooljournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_table")
data class Day(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val date: Int,
    val dayOfTheWeek: String,
    val subject: List<Subject>
)

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dayId: Int,
    val name: String,
    val teacher: String,
    val mark: Int,
    val homework: String
)

@Entity(tableName = "week_table")
data class Week(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dayOfTheWeek: List<Day>,
    val note: Note
)

@Entity(tableName = "week_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val note: String
)