package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gradereportapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var totalSubjects = 0
    private var passed = 0
    private var failed = 0
    private var totalGpa = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            addSubject()
        }
    }

    private fun addSubject() {
        val subject = binding.subjectInput.text.toString()
        val obtainedStr = binding.obtainedInput.text.toString()
        val totalStr = binding.totalInput.text.toString()

        if (subject.isEmpty() || obtainedStr.isEmpty() || totalStr.isEmpty()) {
            return
        }

        val obtained = obtainedStr.toInt()
        val total = totalStr.toInt()

        val percentage = (obtained * 100) / total
        val grade = getGrade(percentage)
        val gpa = getGpa(grade)

        totalSubjects++
        totalGpa += gpa

        if (grade == "F") {
            failed++
        } else {
            passed++
        }

        // Create new row
        val row = TableRow(this)

        val tvSubject = TextView(this)
        val tvObtained = TextView(this)
        val tvTotal = TextView(this)
        val tvGrade = TextView(this)

        tvSubject.text = subject
        tvObtained.text = obtained.toString()
        tvTotal.text = total.toString()
        tvGrade.text = grade

        row.addView(tvSubject)
        row.addView(tvObtained)
        row.addView(tvTotal)
        row.addView(tvGrade)

        // Color highlight
        if (grade == "F") {
            row.setBackgroundColor(Color.RED)
        } else {
            row.setBackgroundColor(Color.GREEN)
        }

        binding.tableLayout.addView(row)

        updateSummary()

        // Clear inputs
        binding.subjectInput.text.clear()
        binding.obtainedInput.text.clear()
        binding.totalInput.text.clear()
    }

    private fun getGrade(percent: Int): String {
        return when (percent) {
            in 90..100 -> "A+"
            in 80..89 -> "A"
            in 70..79 -> "B+"
            in 60..69 -> "B"
            in 50..59 -> "C"
            in 40..49 -> "D"
            else -> "F"
        }
    }

    private fun getGpa(grade: String): Double {
        return when (grade) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }

    private fun updateSummary() {
        binding.summaryText.text =
            "Total: $totalSubjects | Passed: $passed | Failed: $failed"

        val gpa = totalGpa / totalSubjects
        binding.gpaText.text = "GPA: %.2f".format(gpa)
    }
}