package com.example.hustudentunionpresidentvotingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityResultsBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.ResultsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private lateinit var viewModel: ResultsViewModel
    private lateinit var adapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Election Results"

        // Set up RecyclerView
        adapter = ResultsAdapter()
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        binding.rvResults.adapter = adapter

        // Observe results
        lifecycleScope.launch {
            viewModel.resultsState.collectLatest { candidates ->
                adapter.submitList(candidates)
            }
        }
    }
}