package com.example.schooljournal.presentation.scheduleCreateView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.*
import com.example.schooljournal.data.Day
import com.example.schooljournal.presentation.Navigation
import com.example.schooljournal.presentation.NavigationActivity
import kotlinx.android.synthetic.main.fragment_schedule_create.view.*
import java.util.*

class ScheduleCreateFragment : Fragment() {

    private lateinit var viewModel: ScheduleCreateViewModel
    private lateinit var nav: Navigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_schedule_create, container, false)
        viewModel = ViewModelProvider(this).get(ScheduleCreateViewModel::class.java)
        nav = activity as Navigation
        initDayButtons(view)
        initReadyButton(view)
        return view
    }

    private fun initReadyButton(view: View) {
        view.ready_btn.setOnClickListener {
            firstRunFinished()
            viewModel.insertDates()
            viewModel.insertDays()
            viewModel.insertAllData()
            viewModel.insertSubjects("пн", mon)
            viewModel.insertSubjects("вт", tue)
            viewModel.insertSubjects("ср", wed)
            viewModel.insertSubjects("чт", thu)
            viewModel.insertSubjects("пт", fri)
            viewModel.insertSubjects("сб", sat)
            viewModel.insertSubjects("вс", sun)
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
            nav.initNavigation(dayFragments[0])
        }
        view.tu_btn.setOnClickListener {
            nav.initNavigation(dayFragments[1])
        }
        view.we_btn.setOnClickListener {
            nav.initNavigation(dayFragments[2])
        }
        view.th_btn.setOnClickListener {
            nav.initNavigation(dayFragments[3])
        }
        view.fr_btn.setOnClickListener {
            nav.initNavigation(dayFragments[4])
        }
        view.sa_btn.setOnClickListener {
            nav.initNavigation(dayFragments[5])
        }
        view.su_btn.setOnClickListener {
            nav.initNavigation(dayFragments[6])
        }
    }
}