package com.example.hustudentunionpresidentvotingapp.data

data class Candidate(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    var voteCount: Int = 0 // Changed to var for mutability
)