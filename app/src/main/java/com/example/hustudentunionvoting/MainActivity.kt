package com.example.hustudentunionpresidentvotingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hustudentunionpresidentvotingapp.data.CandidateInitializer
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize candidates in Firestore
        runBlocking {
            try {
                CandidateInitializer.initializeCandidates()
                Log.d("MainActivity", "Candidates initialized in Firestore successfully")
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to initialize candidates in Firestore: ${e.message}", e)
            }
        }

        // Navigate to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}