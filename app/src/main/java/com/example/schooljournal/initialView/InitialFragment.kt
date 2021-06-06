package com.example.schooljournal.initialView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.Navigation
import com.example.schooljournal.R
import com.example.schooljournal.scheduleCreateView.ScheduleCreateFragment
import kotlinx.android.synthetic.main.fragment_initial.view.*

class InitialFragment : Fragment() {

    private lateinit var viewModel: InitialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(InitialViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_initial, container, false)
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
        viewModel.getDate()
        viewModel.today.observe(viewLifecycleOwner, Observer {
            view.today_date.text = it
        })
    }
}