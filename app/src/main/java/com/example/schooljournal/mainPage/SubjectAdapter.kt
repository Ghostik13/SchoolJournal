package com.example.schooljournal.mainPage

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.schooljournal.NavigationActivity
import com.example.schooljournal.R
import com.example.schooljournal.data.Subject
import kotlinx.android.synthetic.main.subject_view_holder.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SubjectAdapter(
    private val vm: MainPageViewModel,
    private val onUpdate: (Subject) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

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
        val currentSubject = subjectList[position]
        holder.itemView.subject_name.text = currentSubject.name
        GlobalScope.launch(Dispatchers.IO) {
            val homework = vm.getHomework(currentSubject.id)
            withContext(Dispatchers.Main) {
                holder.itemView.homework_et.setText(homework)
            }
        }
        updateHomework(holder, currentSubject)
        if (currentSubject.photo.isNotBlank()) {
            holder.itemView.add_photo.setImageResource(R.drawable.ic_photo_ok)
            holder.itemView.add_photo.setOnClickListener {
                val dialogFragment = PhotoDialogFragment()
                val bundle = Bundle()
                bundle.putString("photo", currentSubject.photo)
                dialogFragment.arguments = bundle
                val myActivity = (context as NavigationActivity)
                dialogFragment.show(myActivity.supportFragmentManager, "Photo Dialog")
            }
        } else {
            holder.itemView.add_photo.setOnClickListener {
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
        holder.itemView.homework_et.addTextChangedListener(object : TextWatcher {
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

        if (holder.itemView.homework_et.onFocusChangeListener != null) {
            val newHomework = holder.itemView.homework_et.text.toString()
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