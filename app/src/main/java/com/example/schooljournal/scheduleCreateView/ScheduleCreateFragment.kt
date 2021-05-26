package com.example.schooljournal.scheduleCreateView

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.*
import com.example.schooljournal.data.Day
import com.example.schooljournal.data.Subject
import com.example.schooljournal.databinding.FragmentScheduleCreateBinding
import com.example.schooljournal.mainPage.MainPageFragment
import kotlinx.android.synthetic.main.fragment_schedule_create.view.*
import java.util.*

class ScheduleCreateFragment : Fragment() {

    private lateinit var binding: FragmentScheduleCreateBinding
    private lateinit var viewModel: ScheduleCreateViewModel
    private lateinit var nav: Navigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_create, container, false
        )
        viewModel = ViewModelProvider(this).get(ScheduleCreateViewModel::class.java)
        val view = binding.root
        nav = activity as Navigation
        initDayButtons(view)
        initReadyButton(view)
        firstRunFinished()
        return view
    }

    private fun insertData(days: MutableList<Day>) {
        viewModel.insertDays(days)
    }

    private fun initReadyButton(view: View) {
        view.ready_btn.setOnClickListener {
            fragments = createFragments()
            insertDates()
            insertDays()
            insertData(allData)
            insertSubjects(mon, "пн")
            insertSubjects(tue, "вт")
            insertSubjects(wed, "ср")
            insertSubjects(thu, "чт")
            insertSubjects(fri, "пт")
            insertSubjects(sat, "сб")
            insertSubjects(sun, "вс")
            val intent = Intent(requireContext(), NavigationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun firstRunFinished() {
        val sharedPreferences = requireActivity()
            .getSharedPreferences("firstRun", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Done", true)
        editor.apply()
    }

    private fun initDayButtons(view: View) {
        view.mo_btn.setOnClickListener {
            nav.initSchedule(dayFragments[0])
        }
        view.tu_btn.setOnClickListener {
            nav.initSchedule(dayFragments[1])
        }
        view.we_btn.setOnClickListener {
            nav.initSchedule(dayFragments[2])
        }
        view.th_btn.setOnClickListener {
            nav.initSchedule(dayFragments[3])
        }
        view.fr_btn.setOnClickListener {
            nav.initSchedule(dayFragments[4])
        }
        view.sa_btn.setOnClickListener {
            nav.initSchedule(dayFragments[5])
        }
        view.su_btn.setOnClickListener {
            nav.initSchedule(dayFragments[6])
        }
    }

    private val allData = mutableListOf<Day>()
    private val days: MutableList<String> = mutableListOf()
    private val subjects = emptyList<Subject>()

    private fun insertDates() {
        val dateFormat = SimpleDateFormat("yyyyMMdd-EE", Locale.getDefault())
        var calendar = Calendar.getInstance()
        for (i in 0..364) {
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val day = dateFormat.format(calendar.time)
            days.add(day)
            calendar = Calendar.getInstance()
        }
        var dayOk = true
        while (dayOk) {
            if (days[0].substring(9, 11) != "пн") {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val day = dateFormat.format(calendar.time)
                days.add(0, day)
            } else dayOk = false
        }
    }

    private fun insertDays() {
        var week = 1
        for (i in 0 until days.size) {
            val dayName = days[i].substring(9, 11)
            val dayDate = days[i].substring(0, 8).toInt()
            if (i in (0..6)) {
                week = 1
            } else if (dayName == "пн") week++
            val day =
                Day(0, week, dayDate, dayName, subjects)
            allData.add(day)
        }
    }

    private fun insertSubjects(subjectList: MutableList<String>, name: String) {
        val subjects = mutableListOf<Subject>()
        subjectList.forEach {
            if (it.isNotEmpty()) {
                val subject =
                    Subject(0, it, "", "", "", name)
                subjects.add(subject)
            }
        }
        viewModel.insertSubjects(subjects)
        viewModel.updateSubjects(subjects, name)
    }

    private fun createFragments(): ArrayList<Fragment> {
        val fragments = arrayListOf<Fragment>()
        for (i in 1..52) {
            fragments.add(MainPageFragment.newInstance(i))
        }
        return fragments
    }
}