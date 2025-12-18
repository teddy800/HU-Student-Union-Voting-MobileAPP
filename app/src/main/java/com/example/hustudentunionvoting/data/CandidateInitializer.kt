package com.example.hustudentunionpresidentvotingapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object CandidateInitializer {
    private val db = FirebaseFirestore.getInstance()

    suspend fun initializeCandidates() {
        val candidates = listOf(
            Candidate(
                id = "1",
                name = "Abdi Jemal",
                description = "A 3rd-year Law student passionate about student rights and transparency. Abdi aims to strengthen the union by advocating for affordable housing and better academic resources.",
                voteCount = 0
            ),
            Candidate(
                id = "2",
                name = "Selamawit Tesfaye",
                description = "A 4th-year Agriculture student focused on sustainability and inclusivity. Selamawit plans to promote cultural events and support female students through mentorship programs.",
                voteCount = 0
            ),
            Candidate(
                id = "3",
                name = "Dawit Kebede",
                description = "A 3rd-year Computer Science student with a vision for digital transformation. Dawit wants to improve internet access and introduce a student feedback app.",
                voteCount = 0
            ),
            Candidate(
                id = "4",
                name = "Lydya Bekele",
                description = "A 2nd-year Business student dedicated to financial literacy and job opportunities. Lidya proposes workshops and partnerships with local businesses.",
                voteCount = 0
            )
        )

        try {
            candidates.forEach { candidate ->
                db.collection("candidates")
                    .document(candidate.id)
                    .set(candidate)
                    .await()
            }
        } catch (e: Exception) {
            println("Failed to initialize candidates: ${e.message}")
        }
    }
}