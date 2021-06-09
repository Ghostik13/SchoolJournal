package com.example.schooljournal.presentation.settingsView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.R
import com.example.schooljournal.databinding.FragmentSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        view.edit_tv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_editScheduleFragment)
        }
        view.about_tv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
        view.connect_tv.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_connectToDevFragment)
        }
        return view
    }
}