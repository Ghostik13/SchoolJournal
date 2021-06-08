package com.example.schooljournal.presentation.initialView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class InitialViewModel : ViewModel() {

    private val _today: MutableLiveData<String> = MutableLiveData()
    val today: LiveData<String> = _today

    fun getDate(months: Array<String>) {
        val month = convertDate(months)
        val today =
            "Сегодня " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + " " + month
        _today.value = today
    }

    private fun convertDate(months: Array<String>): String? {
        var monthString: String? = null
        when (Calendar.getInstance().get(Calendar.MONTH)) {
            0 -> monthString = months[0]
            1 -> monthString = months[1]
            2 -> monthString = months[2]
            3 -> monthString = months[3]
            4 -> monthString = months[4]
            5 -> monthString = months[5]
            6 -> monthString = months[6]
            7 -> monthString = months[7]
            8 -> monthString = months[8]
            9 -> monthString = months[9]
            10 -> monthString = months[10]
            11 -> monthString = months[11]
        }
        return monthString
    }
}