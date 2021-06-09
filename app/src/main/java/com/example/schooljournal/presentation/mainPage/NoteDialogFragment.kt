package com.example.schooljournal.presentation.mainPage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.R
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.databinding.FragmentNoteDialogBinding
import com.example.schooljournal.databinding.FragmentPhotoDialogBinding
import kotlinx.android.synthetic.main.fragment_note_dialog.view.*

class NoteDialogFragment : DialogFragment() {

    private lateinit var viewModel: MainPageViewModel

    private var _binding: FragmentNoteDialogBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        initNote(view)
        return view
    }

    private fun initNote(view: View) {
        val weekIdNull = this.arguments?.getInt("weekId")
        val weekId: Int = weekIdNull!!.toInt()
        viewModel.getNote(weekId.toString().toInt())
        viewModel.note.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view.note_et.setText(it.note)
            } else {
                val note = Note(0, weekId, "")
                viewModel.insertNote(note)
            }
        })
        view.submit_button.setOnClickListener {
            val text = view.note_et.text.toString()
            val newNote = Note(weekId, weekId, text)
            viewModel.updateNote(newNote)
            dismiss()
        }
    }
}