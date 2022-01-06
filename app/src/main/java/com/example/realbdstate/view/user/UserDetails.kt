package com.example.realbdstate.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivityUserDetailsBinding
import com.example.realbdstate.view.ui.SelectionUi

class UserDetails : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            signNextBtn.setOnClickListener {
                startActivity(Intent(this@UserDetails,SelectionUi::class.java))
            }
        }
    }
}