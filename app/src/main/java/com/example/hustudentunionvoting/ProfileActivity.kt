package com.example.hustudentunionpresidentvotingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityProfileBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Profile"

        // Observe profile
        lifecycleScope.launch {
            viewModel.userProfile.collectLatest { profile ->
                if (profile != null) {
                    binding.tvEmail.text = profile.email
                    binding.tvName.text = profile.name ?: "No name set"
                } else {
                    Toast.makeText(this@ProfileActivity, "No user logged in", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}