package com.example.schooljournal.mainPage

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.schooljournal.R
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val photoPath = intent.getStringExtra("photo").toString()
        Log.d("1", photoPath)
        val takenImage = BitmapFactory.decodeFile(photoPath)
        hw_photo.setImageBitmap(takenImage)
    }
}