package com.example.schooljournal.presentation.mainPage

import android.content.Intent
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
import com.example.schooljournal.data.model.Subject
import com.example.schooljournal.databinding.FragmentPhotoDialogBinding
import com.example.schooljournal.presentation.NavigationActivity
import java.io.File
import java.io.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoDialogFragment : DialogFragment() {

    private var _binding: FragmentPhotoDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainPageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        val rotatedBitmap: Bitmap? = setImage()
        binding.imageHomework.setImageBitmap(rotatedBitmap)
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
        val pathPhoto = this.arguments?.getString("photo").toString()
        val id = this.arguments?.getInt("id")
        val dayId = this.arguments?.getInt("dayId")
        val dayOfWeek = this.arguments?.getInt("dayOfWeek")
        val hw = this.arguments?.getString("hw")
        val name = this.arguments?.getString("name")
        binding.fabDelete.setOnClickListener {
            viewModel.updateHomework(
                Subject(
                    id!!.toInt(),
                    dayId!!.toInt(),
                    name.toString(),
                    hw.toString(),
                    dayOfWeek!!.toInt(),
                    ""
                )
            )
            val file = File(pathPhoto)
            file.delete()
            val intent = Intent(requireContext(), NavigationActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val attrs = dialog!!.window!!.attributes
        attrs.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = attrs
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