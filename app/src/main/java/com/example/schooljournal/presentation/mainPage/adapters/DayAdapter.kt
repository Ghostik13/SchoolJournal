package com.example.schooljournal.presentation.mainPage.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schooljournal.data.model.Day
import com.example.schooljournal.databinding.DayHolderBinding
import com.example.schooljournal.presentation.mainPage.MainPageViewModel
import com.example.schooljournal.utils.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class DayAdapter(private val context: Context, private val vm: MainPageViewModel) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    class DayViewHolder(val binding: DayHolderBinding) : RecyclerView.ViewHolder(binding.root)

    private var dayList = emptyList<Day>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding =
            DayHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val currentDay = dayList[position]
        val month = currentDay.date.toString().substring(4, 6)
        val day = currentDay.date.toString().substring(6, 8)
        val monthString = convertDate(month)
        val parser = Parser("")
        holder.binding.dayTv.text = "${parser.reverseParsingName(currentDay.dayOfTheWeek)},"
        holder.binding.dateTv.text = "$day $monthString"
        recyclerView = holder.binding.recyclerSubjects
        val subjectAdapter = SubjectAdapter(
            vm,
            { subject ->
                vm.updateHomework(subject)
            }, context
        )
        GlobalScope.launch(Dispatchers.IO) {
            val subjects = vm.getCurrentSubjects(currentDay.id)
            withContext(Dispatchers.Main) {
                subjectAdapter.setData(subjects)
            }
        }
        recyclerView.adapter = subjectAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    fun setData(days: List<Day>) {
        this.dayList = days
        notifyDataSetChanged()
    }

    private fun getMonth(i: Int): String {
        var month = Month.of(i).getDisplayName(TextStyle.FULL , Locale.getDefault())
        if(Locale.getDefault() == Locale("ru","RU")) {
            month = "${month.substring(0, month.lastIndex)}я"
        }
        return month
    }

    private fun convertDate(month: String): String? {
        var monthString: String? = null
        when (month) {
            "01" -> monthString = getMonth(1)
            "02" -> monthString = getMonth(2)
            "03" -> monthString = getMonth(3)
            "04" -> monthString = getMonth(4)
            "05" -> monthString = getMonth(5)
            "06" -> monthString = getMonth(6)
            "07" -> monthString = getMonth(7)
            "08" -> monthString = getMonth(8)
            "09" -> monthString = getMonth(9)
            "10" -> monthString = getMonth(10)
            "11" -> monthString = getMonth(11)
            "12" -> monthString = getMonth(12)
        }
        return monthString
    }
}