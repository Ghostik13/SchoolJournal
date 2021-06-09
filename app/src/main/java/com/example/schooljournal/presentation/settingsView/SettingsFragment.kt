package com.example.schooljournal.presentation.settingsView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.R
import com.example.schooljournal.databinding.FragmentSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.editTv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_editScheduleFragment)
        }
        binding.aboutTv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
        binding.connectTv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_connectToDevFragment)
        }
        return view
    }
}