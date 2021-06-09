package com.example.schooljournal.presentation.weekDayView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.schooljournal.*
import com.example.schooljournal.databinding.FragmentWeekDaysBinding
import com.example.schooljournal.presentation.Navigation
import com.example.schooljournal.presentation.scheduleCreateView.ScheduleCreateFragment
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeekDaysFragment : Fragment() {

    private val viewModel: WeekDayViewModel by viewModel()
    private lateinit var navigation: Navigation
    private lateinit var edits: List<EditText>
    private lateinit var editStrings: List<String>
    private lateinit var parser: Parser
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(DAY_NAME).let {
            text = it
        }
    }

    private var _binding: FragmentWeekDaysBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekDaysBinding.inflate(inflater, container, false)
        val view = binding.root
        navigation = requireActivity() as Navigation
        parser = Parser(text.toString())
        view.dow_tv.text = text
        initEditList(view)
        visibilitySet()
        initFab(view)
        initNextButton(view)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                editStrings = edits.map {
                    it.text.toString()
                }
                viewModel.fillOutAllLists(parser.parsingName, editStrings)
                (requireActivity() as Navigation).initNavigation(ScheduleCreateFragment())
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

    private fun initNextButton(view: View) {
        view.next_button.setOnClickListener {
            editStrings = edits.map {
                it.text.toString()
            }
            viewModel.fillOutAllLists(parser.parsingName, editStrings)
            if (parser.parsingName == 6) {
                (requireActivity() as Navigation).initNavigation(ScheduleCreateFragment())
            } else {
                (requireActivity() as Navigation).initNavigation(dayFragments[parser.currentIndex])
            }
        }
    }

    private var flag = 0

    private fun textCheck(day: MutableList<String>) {
        for (i in edits.indices) {
            if (day[i].isNotEmpty()) {
                edits[i].visibility = View.VISIBLE
            }
            if (edits[i].isVisible) {
                flag = i + 1
            }
        }
    }

    private fun visibilitySet() {
        when (parser.parsingName) {
            1 -> textCheck(mon)
            2 -> textCheck(tue)
            3 -> textCheck(wed)
            4 -> textCheck(thu)
            5 -> textCheck(fri)
            6 -> textCheck(sat)
            7 -> textCheck(sun)
        }
    }

    private fun initFab(view: View) {
        val animForFab = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fab_animation
        )
        view.fab_back.startAnimation(animForFab)
        view.fab.setOnClickListener {
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
