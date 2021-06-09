package com.example.schooljournal.presentation.settingsView.scheduleEditView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.R
import com.example.schooljournal.databinding.FragmentEditSchedulereBinding

class EditScheduleFragment : Fragment() {

    private lateinit var dayArray: Array<String>

    private var _binding: FragmentEditSchedulereBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditSchedulereBinding.inflate(inflater, container, false)
        val view = binding.root
        dayArray = resources.getStringArray(R.array.daysFull)
        initDayButtons()
        initReadyButton()
        return view
    }

    private fun initReadyButton() {
        binding.readyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_editScheduleFragment_to_viewPagerFragment)
        }
    }

    private fun initDirection(day: String) {
        val action =
            EditScheduleFragmentDirections.actionEditScheduleFragmentToEditDayFragment(day)
        findNavController().navigate(action)
    }

    private fun initDayButtons() {
        binding.moBtn.setOnClickListener {
            initDirection(dayArray[0])
        }
        binding.tuBtn.setOnClickListener {
            initDirection(dayArray[1])
        }
        binding.weBtn.setOnClickListener {
            initDirection(dayArray[2])
        }
        binding.thBtn.setOnClickListener {
            initDirection(dayArray[3])
        }
        binding.frBtn.setOnClickListener {
            initDirection(dayArray[4])
        }
        binding.saBtn.setOnClickListener {
            initDirection(dayArray[5])
        }
        binding.suBtn.setOnClickListener {
            initDirection(dayArray[6])
        }
    }
}