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
            1 -> monthString = "января"
            2 -> monthString = "февраля"
            3 -> monthString = "марта"
            4 -> monthString = "апреля"
            5 -> monthString = "мая"
            6 -> monthString = "июня"
            7 -> monthString = "июля"
            8 -> monthString = "августа"
            9 -> monthString = "сентября"
            10 -> monthString = "октября"
            11 -> monthString = "ноября"
            12 -> monthString = "декабря"
        }
        return monthString
    }
}