package com.example.schooljournal.weekDayView

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.schooljournal.DAY_NAME
import com.example.schooljournal.Parser
import com.example.schooljournal.dayFragments

class WeekDayViewModel: ViewModel() {

    private var flag = 0

    fun addSubjectField(
        context: Context,
        et1: EditText,
        et2: EditText,
        et3: EditText,
        et4: EditText,
        et5: EditText,
        et6: EditText,
        et7: EditText
    ) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        when (flag) {
            0 -> {
                etActiveOn(inputMethodManager, et1, 0)
                flag = 1
            }
            1 -> {
                etActiveOn(inputMethodManager, et2, 1)
                flag = 2
            }
            2 -> {
                etActiveOn(inputMethodManager, et3, 2)
                flag = 3
            }
            3 -> {
                etActiveOn(inputMethodManager, et4, 3)
                flag = 4
            }
            4 -> {
                etActiveOn(inputMethodManager, et5, 5)
                flag = 5
            }
            5 -> {
                etActiveOn(inputMethodManager, et6, 6)
                flag = 6
            }
            6 -> {
                etActiveOn(inputMethodManager, et7, 7)
            }
        }
    }

    private fun etActiveOn(inputMethodManager: InputMethodManager, et: EditText, flag: Int) {
        et.visibility = View.VISIBLE
        et.requestFocus()
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, flag)
    }

}