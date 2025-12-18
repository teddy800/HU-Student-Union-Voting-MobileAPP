package com.example.hustudentunionpresidentvotingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VotingViewModel : ViewModel() {
    private val _candidates = MutableStateFlow<List<Candidate>>(emptyList())
    val candidates: StateFlow<List<Candidate>> = _candidates

    private val _voteState = MutableStateFlow<Result<Unit>?>(null)
    val voteState: StateFlow<Result<Unit>?> = _voteState

    private val _lastVotedCandidate = MutableStateFlow<Candidate?>(null)
    val lastVotedCandidate: StateFlow<Candidate?> = _lastVotedCandidate

    init {
        _candidates.value = listOf(
            Candidate("1", "Candidate 1", "Description 1", "https://example.com/image1.jpg"),
            Candidate("2", "Candidate 2", "Description 2", "https://example.com/image2.jpg")
        )
    }

    fun submitVote(candidateId: String) {
        try {
            val candidate = _candidates.value.find { it.id == candidateId }
            if (candidate != null) {
                _lastVotedCandidate.value = candidate
            }
            _voteState.value = Result.success(Unit)
        } catch (e: Exception) {
            _voteState.value = Result.failure(e)
        }
    }
}