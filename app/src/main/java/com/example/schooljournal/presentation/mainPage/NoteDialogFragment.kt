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
import com.example.schooljournal.data.model.Note
import com.example.schooljournal.databinding.FragmentNoteDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteDialogFragment : DialogFragment() {

    private val viewModel: MainPageViewModel by viewModel()

    private var _binding: FragmentNoteDialogBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        initNote()
        return view
    }

    private fun initNote() {
        val weekIdNull = this.arguments?.getInt("weekId")
        val weekId: Int = weekIdNull?: 0
        viewModel.getNote(weekId.toString().toInt())
        viewModel.note.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.noteEt.setText(it.note)
            } else {
                val note = Note(0, weekId, "")
                viewModel.insertNote(note)
            }
        })
        binding.submitButton.setOnClickListener {
            val text = binding.noteEt.text.toString()
            val newNote = Note(weekId, weekId, text)
            viewModel.updateNote(newNote)
            dismiss()
        }
    }
}