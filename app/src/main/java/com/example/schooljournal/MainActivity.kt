package com.example.schooljournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var days: List<Day>
    private lateinit var subjects: List<Subject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDb()
        initFragment()
    }

    private fun initFragment() {
        replaceFragment(ScheduleCreateFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun getDb() {
        val taskDao = DayDatabase.getInstance(application).dayDao()
        GlobalScope.launch(Dispatchers.IO) {
            days = taskDao.getDays()
            subjects = taskDao.getSubjects()
        }
    }

    override fun initSchedule(fragment: Fragment) {
        replaceFragment(fragment)
    }

    override fun onBackPressed() {
        initFragment()
    }

    override fun initPrevious() {
        onBackPressed()
    }
}