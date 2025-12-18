package com.example.hustudentunionpresidentvotingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hustudentunionpresidentvotingapp.data.VoteRepository
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityAdminBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.AdminViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: AdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewBinding
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val repository = VoteRepository(FirebaseFirestore.getInstance())
        viewModel = ViewModelProvider(this, AdminViewModelFactory(repository)).get(AdminViewModel::class.java)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Candidate"

        // Set up add button
        binding.btnAddCandidate.setOnClickListener {
            val name = binding.etCandidateName.text.toString().trim()
            val description = binding.etCandidateDescription.text.toString().trim()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                viewModel.addCandidate(name, description)
                Toast.makeText(this, "Candidate added", Toast.LENGTH_SHORT).show()
                binding.etCandidateName.text?.clear()
                binding.etCandidateDescription.text?.clear()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class AdminViewModelFactory(private val repository: VoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { // Line ~50, fixed
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}