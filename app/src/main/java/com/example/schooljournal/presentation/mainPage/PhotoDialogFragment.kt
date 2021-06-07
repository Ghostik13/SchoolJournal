package com.example.schooljournal.presentation.mainPage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.schooljournal.BuildConfig
import com.example.schooljournal.R
import kotlinx.android.synthetic.main.fragment_photo_dialog.view.*
import java.io.IOException

class PhotoDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_dialog, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        val rotatedBitmap: Bitmap? = setImage()
        view.image_homework.setImageBitmap(rotatedBitmap)
        return view
    }

    private fun setImage(): Bitmap? {
        val photoPath = this.arguments?.getString("photo")
        val takenImage = BitmapFactory.decodeFile(photoPath.toString())
        var ei: ExifInterface? = null
        try {
            ei = ExifInterface(photoPath!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (BuildConfig.DEBUG && ei == null) {
            error("Assertion failed")
        }
        val orientation = ei!!.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(takenImage, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(takenImage, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(takenImage, 270f)
            ExifInterface.ORIENTATION_NORMAL -> takenImage
            else -> takenImage
        }
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}