package com.example.schooljournal.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
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