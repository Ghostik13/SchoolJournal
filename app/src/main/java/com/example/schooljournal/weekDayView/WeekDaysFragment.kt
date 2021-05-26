package com.example.schooljournal.weekDayView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.*
import com.example.schooljournal.databinding.FragmentWeekDaysBinding
import com.example.schooljournal.scheduleCreateView.ScheduleCreateFragment
import kotlinx.android.synthetic.main.fragment_week_days.view.*

class WeekDaysFragment : Fragment() {

    private lateinit var viewModel: WeekDayViewModel
    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentWeekDaysBinding
    private lateinit var edits: List<EditText>
    private lateinit var parser: Parser
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(DAY_NAME).let {
            text = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_days, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(WeekDayViewModel::class.java)
        navigation = requireActivity() as Navigation
        parser = Parser(text.toString())
        binding.nameOfDay = text
        initEditList(view)
        visibilitySet()
        initFab()
        initNextButton()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fillOutAllLists(text as String)
                (requireActivity() as Navigation).initSchedule(ScheduleCreateFragment())
                Log.d("ARRAY: ", mon.toString())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return view
    }

    private fun initEditList(view: View) {
        edits = listOf(
            view.first_subject,
            view.second_subject,
            view.third_subject,
            view.fourth_subject,
            view.fifth_subject,
            view.six_subject,
            view.seventh_subject
        )
    }

    private fun addSubjectsToList(list: MutableList<String>) {
        for (i in list.indices) {
            list[i] = edits[i].text.toString()
        }
    }

    private fun initNextButton() {
        binding.root.next_button.setOnClickListener {
            fillOutAllLists(parser.parsingName)
            if (parser.parsingName == "сб") {
                (requireActivity() as Navigation).initSchedule(ScheduleCreateFragment())
            } else {
                (requireActivity() as Navigation).initSchedule(dayFragments[parser.currentIndex])
            }
        }
    }

    private fun fillOutAllLists(nameOfDay: String) {
        when (nameOfDay) {
            "пн" -> addSubjectsToList(mon)
            "вт" -> addSubjectsToList(tue)
            "ср" -> addSubjectsToList(wed)
            "чт" -> addSubjectsToList(thu)
            "пт" -> addSubjectsToList(fri)
            "сб" -> addSubjectsToList(sat)
            "вс" -> addSubjectsToList(sun)
        }
    }

    private var flag = 0

    private fun textCheck(day: MutableList<String>) {
        for (i in edits.indices) {
            if(day[i].isNotEmpty()) {
                edits[i].visibility = View.VISIBLE
            }
            if(edits[i].isVisible) {
                flag = i+1
            }
        }
    }

    private fun visibilitySet() {
        when (parser.parsingName) {
            "пн" -> textCheck(mon)
            "вт" -> textCheck(tue)
            "ср" -> textCheck(wed)
            "чт" -> textCheck(thu)
            "пт" -> textCheck(fri)
            "сб" -> textCheck(sat)
            "вс" -> textCheck(sun)
        }
    }

    private fun initFab() {
        val animForFab = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fab_animation
        )
        binding.root.fab_back.startAnimation(animForFab)
        binding.root.fab.setOnClickListener {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            when (flag) {
                0 -> {
                    etActiveOn(inputMethodManager, edits[0], 0)
                    flag = 1
                }
                1 -> {
                    etActiveOn(inputMethodManager, edits[1], 1)
                    flag = 2
                }
                2 -> {
                    etActiveOn(inputMethodManager, edits[2], 2)
                    flag = 3
                }
                3 -> {
                    etActiveOn(inputMethodManager, edits[3], 3)
                    flag = 4
                }
                4 -> {
                    etActiveOn(inputMethodManager, edits[4], 5)
                    flag = 5
                }
                5 -> {
                    etActiveOn(inputMethodManager, edits[5], 6)
                    flag = 6
                }
                6 -> {
                    etActiveOn(inputMethodManager, edits[6], 7)
                }
            }
        }
    }

    private fun etActiveOn(inputMethodManager: InputMethodManager, et: EditText, flag: Int) {
        if (et.visibility == View.INVISIBLE) {
            et.visibility = View.VISIBLE
        }
        et.requestFocus()
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, flag)
    }

    companion object {
        @JvmStatic
        fun newInstance(dayName: String) =
            WeekDaysFragment().apply {
                arguments = Bundle().apply {
                    putString(DAY_NAME, dayName)
                }
            }
    }
}
