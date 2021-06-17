package com.example.schooljournal.presentation.initialView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.example.schooljournal.presentation.Navigation
import com.example.schooljournal.R
import com.example.schooljournal.databinding.FragmentInitialBinding
import com.example.schooljournal.presentation.scheduleCreateView.ScheduleCreateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialFragment : Fragment() {

    private val viewModel: InitialViewModel by viewModel()
    private lateinit var months: Array<String>
    private lateinit var today: String

    private var _binding: FragmentInitialBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        val view = binding.root
        months = resources.getStringArray(R.array.months)
        today = resources.getString(R.string.today)
        initDate()
        binding.nextBtn.setOnClickListener {
            (requireActivity() as Navigation).initNavigation(ScheduleCreateFragment())
        }
        val animForBeams = AnimationUtils.loadAnimation(
            this.context,
            R.anim.sunny_animation
        )
        binding.ivSun.startAnimation(animForBeams)
        val animForSun = AnimationUtils.loadAnimation(
            this.context,
            R.anim.sun_animation
        )
        binding.ivBeams.startAnimation(animForSun)
        return view
    }

    private fun initDate() {
        val animForDate = AnimationUtils.loadAnimation(
            this.context,
            R.anim.anim_for_date
        )
        binding.todayDate.startAnimation(animForDate)
        viewModel.getDate(months, today)
        viewModel.today.observe(viewLifecycleOwner, Observer {
            binding.todayDate.text = it
        })
    }
}