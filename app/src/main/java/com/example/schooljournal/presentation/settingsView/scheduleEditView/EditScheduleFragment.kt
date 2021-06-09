package com.example.schooljournal.presentation.settingsView.scheduleEditView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooljournal.R
import com.example.schooljournal.databinding.FragmentEditSchedulereBinding
import com.example.schooljournal.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_schedule_create.view.fr_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.mo_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.ready_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.sa_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.su_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.th_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.tu_btn
import kotlinx.android.synthetic.main.fragment_schedule_create.view.we_btn

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
        initDayButtons(view)
        initReadyButton(view)
        return view
    }

    private fun initReadyButton(view: View) {
        view.ready_btn.setOnClickListener {
            findNavController().navigate(R.id.action_editScheduleFragment_to_viewPagerFragment)
        }
    }

    private fun initDirection(day: String) {
        val action =
            EditScheduleFragmentDirections.actionEditScheduleFragmentToEditDayFragment(day)
        findNavController().navigate(action)
    }

    private fun initDayButtons(view: View) {
        view.mo_btn.setOnClickListener {
            initDirection(dayArray[0])
        }
        view.tu_btn.setOnClickListener {
            initDirection(dayArray[1])
        }
        view.we_btn.setOnClickListener {
            initDirection(dayArray[2])
        }
        view.th_btn.setOnClickListener {
            initDirection(dayArray[3])
        }
        view.fr_btn.setOnClickListener {
            initDirection(dayArray[4])
        }
        view.sa_btn.setOnClickListener {
            initDirection(dayArray[5])
        }
        view.su_btn.setOnClickListener {
            initDirection(dayArray[6])
        }
    }
}