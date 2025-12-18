package com.example.hustudentunionpresidentvotingapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VoteRepository(private val db: FirebaseFirestore) {

    suspend fun getAllCandidates(): List<Candidate> {
        return try {
            val snapshot = db.collection("candidates").get().await()
            snapshot.documents.mapNotNull { doc ->
                val voteCount = doc.getLong("voteCount")?.toInt() ?: 0 // Ensure Int
                Candidate(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    voteCount = voteCount
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateVoteCount(candidateId: String, voteCount: Int) {
        try {
            db.collection("candidates")
                .document(candidateId)
                .update("voteCount", voteCount)
                .await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun addCandidate(candidate: Candidate) {
        try {
            db.collection("candidates")
                .document(candidate.id)
                .set(candidate)
                .await()
        } catch (e: Exception) {
            // Handle error
        }
    }
}