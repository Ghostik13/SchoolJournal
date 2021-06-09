package com.example.schooljournal.presentation.settingsView.connectView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schooljournal.databinding.FragmentConnectToDevBinding

class ConnectToDevFragment : Fragment() {

    private var _binding: FragmentConnectToDevBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectToDevBinding.inflate(inflater, container, false)
        return binding.root
    }
}
