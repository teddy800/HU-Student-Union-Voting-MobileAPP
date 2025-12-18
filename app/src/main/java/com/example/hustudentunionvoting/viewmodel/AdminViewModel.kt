package com.example.hustudentunionpresidentvotingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import com.example.hustudentunionpresidentvotingapp.data.VoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AdminViewModel(private val repository: VoteRepository) : ViewModel() {

    private val _candidates = MutableStateFlow<List<Candidate>>(emptyList())
    val candidates: StateFlow<List<Candidate>> = _candidates

    init {
        fetchCandidates()
    }

    private fun fetchCandidates() {
        viewModelScope.launch {
            try {
                val candidateList = repository.getAllCandidates()
                _candidates.value = candidateList
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateVoteCount(candidateId: String, newVoteCount: Int) {
        viewModelScope.launch {
            try {
                repository.updateVoteCount(candidateId, newVoteCount)
                fetchCandidates()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addCandidate(name: String, description: String) {
        viewModelScope.launch {
            try {
                val candidate = Candidate(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    description = description,

                )
                repository.addCandidate(candidate)
                fetchCandidates()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}