package com.example.hustudentunionpresidentvotingapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository(private val db: FirebaseFirestore) {

    suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val doc = db.collection("users").document(userId).get().await()
            if (doc.exists()) {
                UserProfile(
                    uid = doc.id, // Line ~15, fixed
                    email = doc.getString("email") ?: "",
                    name = doc.getString("name")
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserProfile(userProfile: UserProfile) {
        try {
            db.collection("users").document(userProfile.uid).set(
                mapOf(
                    "email" to userProfile.email,
                    "name" to userProfile.name
                )
            ).await()
        } catch (e: Exception) {
            // Handle error
        }
    }
}