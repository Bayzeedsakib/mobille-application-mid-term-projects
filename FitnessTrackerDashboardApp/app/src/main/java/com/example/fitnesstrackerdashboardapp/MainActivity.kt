package com.example.fitnesstrackerdashboardapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stepsText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var updateBtn: Button

    private var steps = 0
    private val goal = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepsText = findViewById(R.id.stepsText)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        updateBtn = findViewById(R.id.updateBtn)

        updateBtn.setOnClickListener {
            showInputDialog()
        }
    }

    private fun showInputDialog() {
        val input = EditText(this)
        input.hint = "Enter steps"

        AlertDialog.Builder(this)
            .setTitle("Update Steps")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val newSteps = input.text.toString().toIntOrNull()
                if (newSteps != null) {
                    steps = newSteps
                    updateUI()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateUI() {
        stepsText.text = "Steps: $steps"

        val progress = (steps * 100) / goal
        progressBar.progress = progress
        progressText.text = "$progress%"

        if (progress >= 100) {
            Toast.makeText(this, "Goal Achieved! 🎉", Toast.LENGTH_SHORT).show()
        }
    }
}