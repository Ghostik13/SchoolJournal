package com.example.schooljournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDaysFragment : Fragment() {


    private lateinit var mondaysId: List<Int>
    private val subjects: MutableList<Subject> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_week_days, container, false)

        val dayDao = activity.let { DayDatabase.getInstance(it!!.application).dayDao() }
        initFab(view)
        view.next_button.setOnClickListener {
            insertSubjects(view, dayDao, "пн")
        }
        return view
    }

    private fun insertSubjects(view: View, dayDao: DayDao, dayOfWeek: String) {
        GlobalScope.launch(Dispatchers.IO) {
            //список айдишников нужного дня недели
            mondaysId = dayDao.getId(dayOfWeek)
            withContext(Dispatchers.Main) {
                //цикл для добавления всех предметов из edit_text в список subjs
                for (i in mondaysId.indices) {
                    addSubjectToList(i, view.first_subject)
                    addSubjectToList(i, view.second_subject)
                    addSubjectToList(i, view.third_subject)
                    addSubjectToList(i, view.fourth_subject)
                    addSubjectToList(i, view.fifth_subject)
                    addSubjectToList(i, view.six_subject)
                    addSubjectToList(i, view.seventh_subject)
                }
            }
            //вставляем список subjects в subject_table
            dayDao.insertSubjects(subjects)
            //цикл для добавления предметов из subject_table в day_table по dayId
            for (i in mondaysId.indices) {
                val list = dayDao.getCurrentSubjects(mondaysId[i])
                dayDao.updateDay(list, mondaysId[i])
            }
        }
        Toast.makeText(requireContext(), "Subject added", Toast.LENGTH_SHORT).show()
    }

    private fun addSubjectToList(i: Int, et: EditText) {
        val nameOfSubject = et.text.toString()
        if (et.text.isNotEmpty()) {
            val subject1 = Subject(0, mondaysId[i], nameOfSubject, " ", " ", " ")
            subjects.add(subject1)
        }
    }

    private fun initFab(view: View) {
        var flag = 0
        view.fab.setOnClickListener {
            when {
                flag == 0 -> {
                    view.first_subject.visibility = View.VISIBLE
                    flag = 1
                }
                flag == 1 -> {
                    view.second_subject.visibility = View.VISIBLE
                    flag = 2
                }
                flag == 2 -> {
                    view.third_subject.visibility = View.VISIBLE
                    flag = 3
                }
                flag == 3 -> {
                    view.fourth_subject.visibility = View.VISIBLE
                    flag = 4
                }
                flag == 4 -> {
                    view.fifth_subject.visibility = View.VISIBLE
                    flag = 5
                }
                flag == 5 -> {
                    view.six_subject.visibility = View.VISIBLE
                    flag = 6
                }
                view.six_subject.visibility == View.VISIBLE -> {
                    view.seventh_subject.visibility = View.VISIBLE
                }
            }
        }
    }
}
