package com.example.schooljournal.WeekDayView

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.schooljournal.*
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.databinding.FragmentWeekDaysBinding
import kotlinx.android.synthetic.main.fragment_week_days.*
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ARG_PARAM1 = "param1"

class WeekDaysFragment : Fragment() {


    private lateinit var currentDayId: List<Int>
    private val subjects: MutableList<Subject> = mutableListOf()

    private lateinit var binding: FragmentWeekDaysBinding

    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_days, container, false)
        val view = binding.root
        val dayDao = activity.let {
            DayDatabase.getInstance(it!!.application).dayDao()
        }
        initFab(view)
        initNextButton(dayDao)
        view.dow_tv.text = text
        loadSubjects(dayDao)
        return view
    }

    private fun loadSubjects(dayDao: DayDao) {
        GlobalScope.launch(Dispatchers.IO) {
            val subjectList = dayDao
                    .loadSubjectsForCurrentDay(Parser(binding.root.dow_tv.text.toString()).parsingName)
                    .subjects
            withContext(Dispatchers.Main) {
                if (subjectList.isNotEmpty()) {
                    binding.root.first_subject.setText(subjectList[0].name)
                }
            }
        }
    }

    private fun initNextButton(dayDao: DayDao) {
        binding.root.next_button.setOnClickListener {
            insertSubjects(binding.root, dayDao, Parser(dow_tv.text.toString()).parsingName)
            if (dow_tv.text.toString() == "Воскресенье") {
                (requireActivity() as Navigation).initSchedule(ScheduleCreateFragment())
            } else (requireActivity() as Navigation).initSchedule(dayFragments[Parser("").currentIndex])
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(dayName: String) =
            WeekDaysFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, dayName)
                }
            }
    }

    private fun insertSubjects(view: View, dayDao: DayDao, dayOfWeek: String) {
        GlobalScope.launch(Dispatchers.IO) {
            //список айдишников нужного дня недели
            currentDayId = dayDao.getId(dayOfWeek)
            withContext(Dispatchers.Main) {
                //цикл для добавления всех предметов из edit_text в список subjs
                for (i in currentDayId.indices) {
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
            for (i in currentDayId.indices) {
                val list = dayDao.getCurrentSubjects(currentDayId[i])
                dayDao.updateDay(list, currentDayId[i])
            }
        }
        Toast.makeText(requireContext(), "Subject added", Toast.LENGTH_SHORT).show()
    }

    private fun addSubjectToList(i: Int, et: EditText) {
        val nameOfSubject = et.text.toString()
        if (et.text.isNotEmpty()) {
            val subject1 = Subject(0, currentDayId[i], nameOfSubject, " ", " ", " ")
            subjects.add(subject1)
        }
    }

    private var flag = 0

    private fun initFab(view: View) {
        view.fab.setOnClickListener {
            addSubjectField()
        }
    }

    private fun addSubjectField() {
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        when (flag) {
            0 -> {
                etActiveOn(inputMethodManager, first_subject, 0)
                flag = 1
            }
            1 -> {
                etActiveOn(inputMethodManager, second_subject, 1)
                flag = 2
            }
            2 -> {
                etActiveOn(inputMethodManager, third_subject, 2)
                flag = 3
            }
            3 -> {
                etActiveOn(inputMethodManager, fourth_subject, 3)
                flag = 4
            }
            4 -> {
                etActiveOn(inputMethodManager, fifth_subject, 5)
                flag = 5
            }
            5 -> {
                etActiveOn(inputMethodManager, six_subject, 6)
                flag = 6
            }
            6 -> {
                etActiveOn(inputMethodManager, seventh_subject, 7)
            }
        }
    }

    private fun etActiveOn(inputMethodManager: InputMethodManager, et: EditText, flag: Int) {
        et.visibility = View.VISIBLE
        et.requestFocus()
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, flag)
    }
}
