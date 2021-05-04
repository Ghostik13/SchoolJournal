package com.example.schooljournal.weekDayView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.schooljournal.*
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.Subject
import com.example.schooljournal.databinding.FragmentWeekDaysBinding
import kotlinx.android.synthetic.main.fragment_week_days.*
import kotlinx.android.synthetic.main.fragment_week_days.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekDaysFragment : Fragment() {

    private lateinit var viewModel: WeekDayViewModel
    private val dayDao: DayDao by lazy {
        activity.let {
            DayDatabase.getInstance(it!!.application).dayDao()
        }
    }
    private lateinit var navigation: Navigation
    private lateinit var binding: FragmentWeekDaysBinding
    private var text: String? = null

    companion object {
        @JvmStatic
        fun newInstance(dayName: String) =
            WeekDaysFragment().apply {
                arguments = Bundle().apply {
                    putString(DAY_NAME, dayName)
                }
            }
    }

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
        navigation = requireActivity() as Navigation
        val view = binding.root
        binding.nameOfDay = text
        viewModel = WeekDayViewModel(dayDao)
        loadSubjects()
        initFab()
        initNextButton()
        return view
    }

    private fun loadSubjects() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.loadSubjects(text.toString())
            withContext(Dispatchers.Main) {
                checkEmpty(binding.root.first_subject, 0)
                checkEmpty(binding.root.second_subject, 1)
                checkEmpty(binding.root.third_subject, 2)
                checkEmpty(binding.root.fourth_subject, 3)
                checkEmpty(binding.root.fifth_subject, 4)
                checkEmpty(binding.root.six_subject, 5)
                checkEmpty(binding.root.seventh_subject, 6)
            }
        }
    }

    private fun checkEmpty(et: EditText, index: Int) {
        val sList = viewModel.subjectList
        if (sList.lastIndex>=index) {
            et.setText(sList[index].name)
            et.visibility = View.VISIBLE
            flag++
        }
    }

    private fun initNextButton() {
        val parser = Parser(text.toString())
        binding.root.next_button.setOnClickListener {
            viewModel.insertSubjects(
                parser.parsingName,
                requireActivity(),
                requireContext(),
                etToString(first_subject),
                etToString(second_subject),
                etToString(third_subject),
                etToString(fourth_subject),
                etToString(fifth_subject),
                etToString(six_subject),
                etToString(seventh_subject)
            )
            viewModel.nextDay(text.toString(), navigation, parser)
        }
    }

    private fun etToString(et: EditText) = et.text.toString()

    private var flag = 0

    private fun initFab() {
        binding.root.fab.setOnClickListener {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            when (flag) {
                0 -> {
                    etActiveOn(inputMethodManager, first_subject, 0)
                    flag = 1
                }
                1 -> {
                    etActiveOn(inputMethodManager, second_subject, 1)
                    flag = 2
                }
                2 -> {
                    etActiveOn(inputMethodManager, third_subject, 2)
                    flag = 3
                }
                3 -> {
                    etActiveOn(inputMethodManager, fourth_subject, 3)
                    flag = 4
                }
                4 -> {
                    etActiveOn(inputMethodManager, fifth_subject, 5)
                    flag = 5
                }
                5 -> {
                    etActiveOn(inputMethodManager, six_subject, 6)
                    flag = 6
                }
                6 -> {
                    etActiveOn(inputMethodManager, seventh_subject, 7)
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
