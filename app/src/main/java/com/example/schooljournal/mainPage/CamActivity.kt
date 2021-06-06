package com.example.schooljournal.mainPage

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.schooljournal.CAMERA_REQUEST
import com.example.schooljournal.NavigationActivity
import com.example.schooljournal.R
import com.example.schooljournal.data.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class CamActivity : AppCompatActivity() {

    private lateinit var photoFile: File
    private lateinit var viewModel: MainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile()
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        val fileProvider =
            FileProvider.getUriForFile(this, "com.example.schooljournal.fileprovider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        try {
            ActivityCompat.startActivityForResult(this, intent, CAMERA_REQUEST, null)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun getPhotoFile(): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("hwork", ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = intent.getStringExtra("name").toString()
            val dayOfWeek = intent.getStringExtra("dayOfWeek").toString()
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