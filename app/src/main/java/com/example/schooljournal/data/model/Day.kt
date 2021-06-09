package com.example.schooljournal.data.model

import androidx.room.*
import com.example.schooljournal.data.converters.SubjectConverter

@Entity(tableName = "day_table")
data class Day(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val date: Int,
    val dayOfTheWeek: Int,
    @TypeConverters(SubjectConverter::class)
    val subjects: List<Subject>
)

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dayId: Int,
    val name: String,
    val homework: String,
    val dayOfWeek: Int,
    val photo: String
)

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekId: Int,
    val note: String
)