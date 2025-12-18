package com.example.hustudentunionpresidentvotingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityAdminDashboardBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.ResultsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var viewModel: ResultsViewModel
    private lateinit var adapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Admin Dashboard"

        // Set up RecyclerView
        adapter = ResultsAdapter()
        binding.rvVoteCounts.layoutManager = LinearLayoutManager(this)
        binding.rvVoteCounts.adapter = adapter

        // Show loading
        binding.tvTotalVotes.text = "Loading..."
        binding.rvVoteCounts.visibility = View.GONE

        // Observe results
        lifecycleScope.launch {
            viewModel.resultsState.collectLatest { candidates: List<Candidate> ->
                binding.rvVoteCounts.visibility = View.VISIBLE
                if (candidates.isEmpty()) {
                    binding.tvTotalVotes.text = "No candidates found"
                } else {
                    adapter.submitList(candidates)
                    binding.tvTotalVotes.text = "Total Votes: ${candidates.sumOf { it.voteCount.toInt() }}"
                }
            }
        }

        // Navigate to other activities
        val email = intent.getStringExtra("user_email") ?: "No email provided"
        binding.btnViewProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java).apply {
                putExtra("user_email", email)
            })
        }
        binding.btnViewResults.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java).apply {
                putExtra("user_email", email)
            })
        }
    }
}