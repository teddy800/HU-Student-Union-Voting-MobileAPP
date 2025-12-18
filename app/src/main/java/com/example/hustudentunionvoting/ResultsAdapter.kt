package com.example.hustudentunionpresidentvotingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import com.example.hustudentunionpresidentvotingapp.databinding.ItemResultBinding
import com.example.hustudentunionpresidentvotingapp.viewmodel.ResultsViewModel

class ResultsAdapter : ListAdapter<Candidate, ResultsAdapter.ResultViewHolder>(CandidateDiffCallback()) {

    private val viewModel = ResultsViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ResultViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(candidate: Candidate) {
            binding.tvCandidateName.text = candidate.name
            binding.tvVoteCount.text = "Votes: ${candidate.voteCount}"
            binding.btnVote.setOnClickListener {
                viewModel.castVote(candidate.id)
            }
        }
    }
}

class CandidateDiffCallback : DiffUtil.ItemCallback<Candidate>() {
    override fun areItemsTheSame(oldItem: Candidate, newItem: Candidate): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Candidate, newItem: Candidate): Boolean {
        return oldItem == newItem
    }
}