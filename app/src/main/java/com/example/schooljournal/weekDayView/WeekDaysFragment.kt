package com.example.schooljournal.weekDayView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

class WeekDaysFragment : Fragment() {

    private lateinit var viewModel: WeekDayViewModel
    private lateinit var currentDayId: List<Int>
    private val subjects: MutableList<Subject> = mutableListOf()
    private lateinit var dayDao: DayDao

    private var text: String? = null

    private lateinit var binding: FragmentWeekDaysBinding

    companion object {
        @JvmStatic
        fun newInstance(dayName: String) =
            WeekDaysFragment().apply {
                arguments = Bundle().apply {
                    putString(DAY_NAME, dayName)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(DAY_NAME).let {
            text = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_days, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(WeekDayViewModel::class.java)
        binding.nameOfDay = text
        dayDao = activity.let {
            DayDatabase.getInstance(it!!.application).dayDao()
        }
        initFab(view)
        initNextButton(dayDao)
        loadSubjects()
        return view
    }

    private fun loadSubjects() {
        GlobalScope.launch(Dispatchers.IO) {
            val subjectList = dayDao
                .loadSubjectsForCurrentDay(Parser(text.toString()).parsingName)
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

    private fun initFab(view: View) {
        view.fab.setOnClickListener {
            viewModel.addSubjectField(
                requireContext(),
                first_subject,
                second_subject,
                third_subject,
                fourth_subject,
                fifth_subject,
                six_subject,
                seventh_subject
            )
        }
    }
}
