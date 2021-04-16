package com.example.schooljournal.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ToFromSubjectConverter {

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