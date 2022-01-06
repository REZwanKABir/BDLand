package com.example.realbdstate.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivityPropertyDetailsBinding

class PropertyDetails : AppCompatActivity() {
    private lateinit var binding: ActivityPropertyDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}