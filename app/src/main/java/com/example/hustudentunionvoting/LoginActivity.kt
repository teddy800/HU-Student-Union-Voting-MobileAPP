package com.example.hustudentunionpresidentvotingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (isValidEmail(email) && isValidPassword(password)) {
                startActivity(Intent(this, AdminDashboardActivity::class.java).apply {
                    putExtra("user_email", email)
                })
                finish()
            } else {
                Toast.makeText(this, "Please enter a valid email and password (min 6 characters)", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (isValidEmail(email) && isValidPassword(password)) {
                startActivity(Intent(this, AdminDashboardActivity::class.java).apply {
                    putExtra("user_email", email)
                })
                finish()
            } else {
                Toast.makeText(this, "Please enter a valid email and password (min 6 characters)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}