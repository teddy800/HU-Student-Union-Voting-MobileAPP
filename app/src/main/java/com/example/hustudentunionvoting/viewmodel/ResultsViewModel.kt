package com.example.hustudentunionpresidentvotingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hustudentunionpresidentvotingapp.data.Candidate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultsViewModel : ViewModel() {
    private val _resultsState = MutableStateFlow<List<Candidate>>(emptyList())
    val resultsState: StateFlow<List<Candidate>> = _resultsState
    private val candidates = mutableListOf<Candidate>()

    init {
        initializeCandidates()
    }

    private fun initializeCandidates() {
        viewModelScope.launch {
            candidates.clear()
            candidates.addAll(listOf(
                Candidate(id = "1", name = "Abdi Jemal", description = "A 3rd-year Law student passionate about student rights and transparency. Abdi aims to strengthen the union by advocating for affordable housing and better academic resources.", voteCount = 0),
                Candidate(id = "2", name = "Selamawit Tesfaye", description = "A 4th-year Agriculture student focused on sustainability and inclusivity. Selamawit plans to promote cultural events and support female students through mentorship programs.", voteCount = 0),
                Candidate(id = "3", name = "Dawit Kebede", description = "A 3rd-year Computer Science student with a vision for digital transformation. Dawit wants to improve internet access and introduce a student feedback app.", voteCount = 0),
                Candidate(id = "4", name = "Lidya Bekele", description = "A 2nd-year Business student dedicated to financial literacy and job opportunities. Lidya proposes workshops and partnerships with local businesses.", voteCount = 0)
            ))
            _resultsState.value = candidates.toList() // Create new list to trigger update
        }
    }

    fun castVote(candidateId: String) {
        viewModelScope.launch {
            val candidateIndex = candidates.indexOfFirst { it.id == candidateId }
            if (candidateIndex != -1) {
                candidates[candidateIndex].voteCount += 1
                _resultsState.value = candidates.toList() // Create new list to trigger update
                Log.d("ResultsViewModel", "Voted for ${candidates[candidateIndex].name}, new count: ${candidates[candidateIndex].voteCount}")
            } else {
                Log.w("ResultsViewModel", "Candidate with ID $candidateId not found")
            }
        }
    }
}