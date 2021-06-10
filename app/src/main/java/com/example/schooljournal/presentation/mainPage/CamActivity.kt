package com.example.schooljournal.presentation.mainPage

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.presentation.CAMERA_REQUEST
import com.example.schooljournal.presentation.NavigationActivity
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.databinding.ActivityCamBinding
import com.example.schooljournal.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import org.koin.androidx.viewmodel.ext.android.viewModel

class CamActivity : AppCompatActivity() {

    private lateinit var photoFile: File
    private val viewModel: MainPageViewModel by viewModel()

    private lateinit var binding: ActivityCamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile()
        val fileProvider =
            FileProvider.getUriForFile(this, "com.example.schooljournal.fileprovider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        try {
            ActivityCompat.startActivityForResult(this, intent, CAMERA_REQUEST, null)
        } catch (e: ActivityNotFoundException) {
            this.toast("Что-то пошло не так...")
        }
    }

    private fun getPhotoFile(): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("hwork", ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = intent.getStringExtra("name").toString()
            val dayOfWeek = intent.getIntExtra("dayOfWeek", 0)
            val id = intent.getIntExtra("id", 0)
            val dayId = intent.getIntExtra("dayId", 0)
            val homework = intent.getStringExtra("hw").toString()
            val photoPath = photoFile.absolutePath
            val updatedSubject =
                Subject(id, dayId, name, homework, dayOfWeek, photoPath)
            Log.d("Subject", updatedSubject.toString())
            viewModel.updateHomework(updatedSubject)
            GlobalScope.launch(Dispatchers.IO) {
                val subjects = viewModel.getCurrentSubjects(dayId)
                Log.d("UpdSubject", subjects.toString())
            }
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}