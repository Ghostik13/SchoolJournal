package com.example.schooljournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.databinding.FragmentScheduleCreateBinding
import kotlinx.android.synthetic.main.fragment_schedule_create.view.*

class ScheduleCreateFragment : Fragment() {

    private lateinit var binding: FragmentScheduleCreateBinding
    private lateinit var viewModel: ScheduleCreateViewModel
    private lateinit var nav: Navigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_create, container, false)
        viewModel = ViewModelProvider(this).get(ScheduleCreateViewModel::class.java)
        val view = binding.root
        nav = activity as Navigation
        view.mo_btn.setOnClickListener {
            viewModel.initMonday(nav)
        }
        view.tu_btn.setOnClickListener {
            viewModel.initTuesday(nav)
        }
        view.we_btn.setOnClickListener {
            viewModel.initWednesday(nav)
        }
        view.th_btn.setOnClickListener {
            viewModel.initThursday(nav)
        }
        view.fr_btn.setOnClickListener {
            viewModel.initFriday(nav)
        }
        view.sa_btn.setOnClickListener {
            viewModel.initSaturday(nav)
        }
        view.su_btn.setOnClickListener {
            viewModel.initSunday(nav)
        }
        return view
    }
}