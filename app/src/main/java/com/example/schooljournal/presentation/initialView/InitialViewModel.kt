package com.example.schooljournal.presentation.initialView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class InitialViewModel : ViewModel() {

    private val _today: MutableLiveData<String> = MutableLiveData()
    val today: LiveData<String> = _today

    fun getDate(months: Array<String>, todayStr: String) {
        val month = convertDate(months)
        val today = "$todayStr ${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)} $month"
        _today.value = today
    }

    private fun convertDate(months: Array<String>): String? {
        var monthString: String? = null
        Calendar.getInstance().get(Calendar.MONTH).let {
            if (it < 0 || it > 11) return@let
            monthString = months[it]
        }
        return monthString
    }
}