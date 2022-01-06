package com.example.realbdstate.view.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivityUploadPropertyBinding

class UploadProperty : AppCompatActivity() {
    private lateinit var binding: ActivityUploadPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}