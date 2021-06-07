package com.example.schooljournal.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooljournal.Parser
import com.example.schooljournal.R
import kotlinx.android.synthetic.main.fragment_edit_day.view.*
import kotlinx.android.synthetic.main.fragment_week_days.view.dow_tv
import kotlinx.android.synthetic.main.fragment_week_days.view.fab_back
import kotlinx.android.synthetic.main.fragment_week_days.view.next_button

class EditDayFragment : Fragment() {

    private val args by navArgs<EditDayFragmentArgs>()
    private lateinit var dayArray: Array<String>
    private lateinit var edits: List<EditText>
    private lateinit var viewModel: EditDayViewModel
    private lateinit var parser: Parser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_day, container, false)
        viewModel = ViewModelProvider(this).get(EditDayViewModel::class.java)
        view.dow_tv.text = args.NameOfDay
        parser = Parser(args.NameOfDay)
        dayArray = resources.getStringArray(R.array.daysFull)
        initNextButton(view)
        initEditList(view)
        initSubjects()
        initFab(view)
        return view
    }

    private fun initNextButton(view: View) {
        view.next_button.setOnClickListener {
            viewModel.updateSubjects(
                edits[0].text.toString(),
                edits[1].text.toString(),
                edits[2].text.toString(),
                edits[3].text.toString(),
                edits[4].text.toString(),
                edits[5].text.toString(),
                edits[6].text.toString(),
                parser.parsingName
            )
            val currentIndex = dayArray.indexOf(args.NameOfDay)
            val nextIndex = currentIndex + 1
            if (args.NameOfDay == dayArray.last()) {
                findNavController().navigate(R.id.action_editDayFragment_to_editScheduleFragment)
            } else {
                val action =
                    EditDayFragmentDirections.actionEditDayFragmentSelf(dayArray[nextIndex])
                findNavController().navigate(action)
            }
        }
    }

    private fun initEditList(view: View) {
        edits = listOf(
            view.first_subject_edit,
            view.second_subject_edit,
            view.third_subject_edit,
            view.fourth_subject_edit,
            view.fifth_subject_edit,
            view.six_subject_edit,
            view.seventh_subject_edit
        )
    }

    private fun insertSubjects(subjects: List<String>, index: Int) {
        if (subjects.size > index) {
            edits[index].visibility = View.VISIBLE
            flag++
            edits[index].setText(subjects[index])
        }
    }

    private fun initSubjects() {
        viewModel.getSubjectsForCurrentDay(parser.parsingName)
        viewModel.subjectNames.observe(viewLifecycleOwner, Observer {
            for (i in 0..6) {
                insertSubjects(it, i)
            }
            viewModel.fillLists(
                edits[0].text.toString(),
                edits[1].text.toString(),
                edits[2].text.toString(),
                edits[3].text.toString(),
                edits[4].text.toString(),
                edits[5].text.toString(),
                edits[6].text.toString(),
                parser.parsingName
            )
        })

    }

    private var flag = 0

    private fun initFab(view: View) {
        val animForFab = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fab_animation
        )
        view.fab_back.startAnimation(animForFab)
        view.fab_edit.setOnClickListener {
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
}