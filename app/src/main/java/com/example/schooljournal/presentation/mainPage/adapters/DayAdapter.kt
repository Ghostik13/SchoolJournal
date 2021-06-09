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
        holder.binding.dayTv.text = parser.reverseParsingName(currentDay.dayOfTheWeek) + ","
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

    private fun convertDate(month: String): String? {
        var monthString: String? = null
        when (month) {
            "01" -> monthString = "января"
            "02" -> monthString = "февраля"
            "03" -> monthString = "марта"
            "04" -> monthString = "апреля"
            "05" -> monthString = "мая"
            "06" -> monthString = "июня"
            "07" -> monthString = "июля"
            "08" -> monthString = "августа"
            "09" -> monthString = "сентября"
            "10" -> monthString = "октября"
            "11" -> monthString = "ноября"
            "12" -> monthString = "декабря"
        }
        return monthString
    }
}