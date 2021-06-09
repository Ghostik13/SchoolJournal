package com.example.schooljournal.presentation.scheduleCreateView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schooljournal.utils.*
import com.example.schooljournal.databinding.FragmentScheduleCreateBinding
import com.example.schooljournal.presentation.Navigation
import com.example.schooljournal.presentation.NavigationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleCreateFragment : Fragment() {

    private val viewModel: ScheduleCreateViewModel by viewModel()
    private lateinit var nav: Navigation

    private var _binding: FragmentScheduleCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        nav = activity as Navigation
        initDayButtons()
        initReadyButton()
        return view
    }

    private fun initReadyButton() {
        binding.readyBtn.setOnClickListener {
            firstRunFinished()
            viewModel.insertAllData()
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

    private fun initDayButtons() {
        with(binding) {
            moBtn.setOnClickListener {
                nav.initNavigation(dayFragments[0])
            }
            tuBtn.setOnClickListener {
                nav.initNavigation(dayFragments[1])
            }
            weBtn.setOnClickListener {
                nav.initNavigation(dayFragments[2])
            }
            thBtn.setOnClickListener {
                nav.initNavigation(dayFragments[3])
            }
            frBtn.setOnClickListener {
                nav.initNavigation(dayFragments[4])
            }
            saBtn.setOnClickListener {
                nav.initNavigation(dayFragments[5])
            }
            suBtn.setOnClickListener {
                nav.initNavigation(dayFragments[6])
            }
        }
    }
}