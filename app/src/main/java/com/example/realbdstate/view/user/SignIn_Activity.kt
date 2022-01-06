package com.example.realbdstate.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realbdstate.R
import com.example.realbdstate.databinding.ActivitySignInBinding
import com.example.realbdstate.view.ui.SelectionUi

class SignIn_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            signInWithGoogle.setOnClickListener {
                startActivity(Intent(this@SignIn_Activity,UserDetails::class.java))
            }
        }
    }
}