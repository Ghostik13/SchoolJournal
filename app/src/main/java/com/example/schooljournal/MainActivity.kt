package com.example.schooljournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var days: List<Day>
    private lateinit var subjects: List<Subject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskDao = DayDatabase.getInstance(application).dayDao()

        GlobalScope.launch(Dispatchers.IO) {
            days = taskDao.getDays()
            subjects = taskDao.getSubjects()
        }
    }
}