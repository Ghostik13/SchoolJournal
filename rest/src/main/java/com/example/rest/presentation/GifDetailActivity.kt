package com.example.rest.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.rest.Constants
import com.example.rest.R
import com.example.rest.databinding.ActivityGifDetailBinding

class GifDetailActivity : AppCompatActivity() {

    private var currentGif: String = ""
    private var currentGifTitle: String = ""
    private lateinit var clipboard: ClipboardManager

    private lateinit var binding: ActivityGifDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGifDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initGif(intent)
        Glide
            .with(this)
            .load(currentGif)
            .into(binding.gifImage)
        binding.gifTitle.text = currentGifTitle
        initButtons()
    }

    private fun initButtons() {
        binding.shareBtn.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, currentGif)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        binding.copyBtn.setOnClickListener {
            val clip = ClipData.newPlainText("CopiedText", currentGif)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                R.string.copy,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initGif(intent: Intent) {
        val bundle = intent.extras
        if (bundle != null) {
            currentGif = bundle.getString(Constants.CURRENT_GIF_URL)!!
            currentGifTitle = bundle.getString(Constants.CURRENT_GIF_TITLE)!!
        }
    }
}