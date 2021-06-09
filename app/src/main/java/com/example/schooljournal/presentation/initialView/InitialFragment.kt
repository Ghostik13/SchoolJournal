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
import kotlinx.android.synthetic.main.fragment_initial.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitialFragment : Fragment() {

    private val viewModel: InitialViewModel by viewModel()
    private lateinit var months: Array<String>

    private var _binding: FragmentInitialBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        val view = binding.root
        months = resources.getStringArray(R.array.months)
        initDate(view)
        view.next_btn.setOnClickListener {
            (requireActivity() as Navigation).initNavigation(ScheduleCreateFragment())
        }
        return view
    }

    private fun initDate(view: View) {
        val animForDate = AnimationUtils.loadAnimation(
            this.context,
            R.anim.anim_for_date
        )
        view.today_date.startAnimation(animForDate)
        viewModel.getDate(months)
        viewModel.today.observe(viewLifecycleOwner, Observer {
            view.today_date.text = it
        })
    }
}