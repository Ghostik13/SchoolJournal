package com.example.schooljournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import kotlinx.android.synthetic.main.fragment_week_days.*
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDaysFragment : Fragment() {

    private lateinit var days: List<Day>
    private lateinit var mondaysId: List<Int>
    private val subjs: MutableList<Subject> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_week_days, container, false)

        val dayDao = activity.let { DayDatabase.getInstance(it!!.application).dayDao() }

        view.add_button.setOnClickListener {
            val nameOfFirstSubject = view.first_subject.text.toString()
            val nameOfSecondSubject = view.second_subject.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                days = dayDao.getData()
                mondaysId = dayDao.getId("Mo")
                withContext(Dispatchers.Main) {
                    for (i in 0 until days.size / 7) {
                        val subject1 = Subject(0, mondaysId[i], nameOfFirstSubject, " ", " ", " ")
                        val subject2 = Subject(0, mondaysId[i], nameOfSecondSubject, " ", " ", " ")
                        subjs.add(subject1)
                        subjs.add(subject2)
                    }
                }
                dayDao.insertSubjects(subjs)
                for (i in mondaysId.indices) {
                    val list = dayDao.getCurrentSubjects(mondaysId[i])
                    dayDao.updateDay(list, mondaysId[i])
                }
            }
            Toast.makeText(requireContext(), "Subject added", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}
