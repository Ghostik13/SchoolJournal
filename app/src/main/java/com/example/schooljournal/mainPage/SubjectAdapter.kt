package com.example.schooljournal.mainPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schooljournal.R
import com.example.schooljournal.data.Subject
import kotlinx.android.synthetic.main.subject_view_holder.view.*

class SubjectAdapter: RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var subjectList = emptyList<Subject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.subject_view_holder,
                parent,
                false
            )
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val cs = subjectList[position]
        holder.itemView.subject_name.text = cs.name
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    fun setData(subjects: List<Subject>) {
        this.subjectList = subjects
        notifyDataSetChanged()
    }
}