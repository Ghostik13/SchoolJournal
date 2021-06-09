package com.example.schooljournal.presentation

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.schooljournal.databinding.FragmentIntroBinding
import com.example.schooljournal.presentation.initialView.InitialFragment
import com.example.schooljournal.toast

const val CAMERA_REQUEST = 102
const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        val view = binding.root
        Handler(Looper.getMainLooper()).postDelayed({
            (requireActivity() as Navigation).initNavigation(InitialFragment())
        }, 3000)
        checkForCameraPermission()
        return view
    }

    private fun checkForCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                CAMERA_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                requireContext().toast(requireContext(), "Permission granted")
            }
            shouldShowRequestPermissionRationale(CAMERA_PERMISSION) -> showDialog()
            else -> ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA_PERMISSION),
                CAMERA_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck() {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requireContext().toast(requireContext(), "Camera permission refused")
            } else requireContext().toast(requireContext(), "Camera permission granted")
        }

        when (requestCode) {
            CAMERA_REQUEST -> innerCheck()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage("Permission to access your camera")
            setTitle("Permission required")
            setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(CAMERA_PERMISSION),
                    CAMERA_REQUEST
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}