package com.example.schooljournal.data.converters

import androidx.room.TypeConverter
import com.example.schooljournal.data.model.Subject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubjectConverter {

    @TypeConverter
    fun fromSubjectList(subjects: List<Subject?>?): String? {
        if (subjects == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Subject?>?>() {}.type
        return gson.toJson(subjects, type)
    }

    @TypeConverter
    fun toSubjectList(subjectString: String?): List<Subject>? {
        if (subjectString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Subject?>?>() {}.type
        return gson.fromJson<List<Subject>>(subjectString, type)
    }
}