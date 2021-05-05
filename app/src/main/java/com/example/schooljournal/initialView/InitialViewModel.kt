package com.example.schooljournal.initialView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class InitialViewModel : ViewModel() {

    private val _today: MutableLiveData<String> = MutableLiveData()
    val today: LiveData<String> = _today

    fun getDate() {
        val month = convertDate()
        val today =
            "Сегодня " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + " " + month
        _today.value = today
    }

    private fun convertDate(): String? {
        var monthString: String? = null
        when (Calendar.getInstance().get(Calendar.MONTH)) {
            0 -> monthString = "января"
            1 -> monthString = "февраля"
            2 -> monthString = "марта"
            3 -> monthString = "апреля"
            4 -> monthString = "мая"
            5 -> monthString = "июня"
            6 -> monthString = "июля"
            7 -> monthString = "августа"
            8 -> monthString = "сентября"
            9 -> monthString = "октября"
            10 -> monthString = "ноября"
            11 -> monthString = "декабря"
        }
        return monthString
    }
}