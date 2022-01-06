package com.example.realbdstate.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivitySelectionUiBinding
import com.example.realbdstate.view.user.UploadProperty

class SelectionUi : AppCompatActivity() {
    private lateinit var binding: ActivitySelectionUiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            uploadProperty.setOnClickListener {
                startActivity(Intent(this@SelectionUi,UploadProperty::class.java))
            }
            explore.setOnClickListener {
               startActivity(Intent(this@SelectionUi,MainActivity::class.java))
            }
        }
    }
}