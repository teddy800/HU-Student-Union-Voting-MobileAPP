package com.example.hustudentunionpresidentvotingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import com.example.hustudentunionpresidentvotingapp.databinding.ActivityVotingBinding
import com.example.hustudentunionpresidentvotingapp.databinding.ItemCandidateBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.VotingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VotingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVotingBinding
    private lateinit var viewModel: VotingViewModel
    private lateinit var adapter: CandidateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVotingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("VotingActivity", "onCreate called")

        viewModel = ViewModelProvider(this)[VotingViewModel::class.java]

        adapter = CandidateAdapter { candidate ->
            viewModel.submitVote(candidate.id)
        }
        binding.rvCandidates.layoutManager = LinearLayoutManager(this)
        binding.rvCandidates.adapter = adapter

        binding.tvTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))

        lifecycleScope.launch {
            Log.d("VotingActivity", "Starting to collect candidates")
            viewModel.candidates.collectLatest { candidates ->
                Log.d("VotingActivity", "Candidates received: $candidates")
                adapter.submitList(candidates)
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.voteState.collectLatest { result ->
                result?.let {
                    if (it.isSuccess) {
                        val lastVoted = viewModel.lastVotedCandidate.value
                        val intent = Intent(this@VotingActivity, ResultsActivity::class.java)
                        intent.putExtra("candidate_name", lastVoted?.name)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@VotingActivity, "Vote failed: ${it.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

class CandidateAdapter(private val onVoteClick: (Candidate) -> Unit) :
    ListAdapter<Candidate, CandidateAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Candidate>() {
        override fun areItemsTheSame(oldItem: Candidate, newItem: Candidate): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Candidate, newItem: Candidate): Boolean = oldItem == newItem
    }) {

    class ViewHolder(val binding: ItemCandidateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCandidateBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = getItem(position)
        Log.d("CandidateAdapter", "Binding candidate: $candidate")
        with(holder.binding) {
            tvName.text = candidate.name
            tvDescription.text = candidate.description
            candidate.imageUrl?.let { url ->
                Glide.with(root.context)
                    .load(url)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(ivCandidate)
            }
            btnVote.setOnClickListener { onVoteClick(candidate) }
        }
    }
}