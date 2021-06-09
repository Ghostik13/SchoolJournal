package com.example.schooljournal.presentation.mainPage.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.schooljournal.presentation.NavigationActivity
import com.example.schooljournal.R
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.databinding.SubjectViewHolderBinding
import com.example.schooljournal.presentation.mainPage.CamActivity
import com.example.schooljournal.presentation.mainPage.MainPageViewModel
import com.example.schooljournal.presentation.mainPage.PhotoDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubjectAdapter(
    private val vm: MainPageViewModel,
    private val onUpdate: (Subject) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(val binding: SubjectViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var subjectList = emptyList<Subject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding =
            SubjectViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val currentSubject = subjectList[position]
        holder.itemView.findViewById<TextView>(R.id.subject_name).text = currentSubject.name
        GlobalScope.launch(Dispatchers.IO) {
            val homework = vm.getHomework(currentSubject.id)
            withContext(Dispatchers.Main) {
                holder.binding.homeworkEt.setText(homework)
            }
        }
        updateHomework(holder, currentSubject)
        if (currentSubject.photo.isNotBlank()) {
            holder.binding.addPhoto.setImageResource(R.drawable.ic_photo_ok)
            holder.binding.addPhoto.setOnClickListener {
                val dialogFragment = PhotoDialogFragment()
                val bundle = Bundle()
                bundle.putString("photo", currentSubject.photo)
                dialogFragment.arguments = bundle
                val myActivity = (context as NavigationActivity)
                dialogFragment.show(myActivity.supportFragmentManager, "Photo Dialog")
            }
        } else {
            holder.binding.addPhoto.setOnClickListener {
                val intent = Intent(context, CamActivity::class.java)
                intent.putExtra("dayId", currentSubject.dayId)
                intent.putExtra("dayOfWeek", currentSubject.dayOfWeek)
                intent.putExtra("hw", currentSubject.homework)
                intent.putExtra("id", currentSubject.id)
                intent.putExtra("name", currentSubject.name)
                startActivity(context, intent, null)
            }
        }
    }

    private fun updateHomework(
        holder: SubjectViewHolder,
        currentSubject: Subject
    ) {
        holder.binding.homeworkEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val timer = object : CountDownTimer(10000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        val newHomework = s.toString()
                        val updatedSubject = Subject(
                            currentSubject.id,
                            currentSubject.dayId,
                            currentSubject.name,
                            newHomework,
                            currentSubject.dayOfWeek,
                            currentSubject.photo
                        )
                        onUpdate(updatedSubject)
                    }
                }
                timer.start()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        if (holder.binding.homeworkEt.onFocusChangeListener != null) {
            val newHomework = holder.binding.homeworkEt.text.toString()
            val updatedSubject = Subject(
                currentSubject.id,
                currentSubject.dayId,
                currentSubject.name,
                newHomework,
                currentSubject.dayOfWeek,
                currentSubject.photo
            )
            onUpdate(updatedSubject)
        }
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    fun setData(subjects: List<Subject>) {
        this.subjectList = subjects
        notifyDataSetChanged()
    }
}