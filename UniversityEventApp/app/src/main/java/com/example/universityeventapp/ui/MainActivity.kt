package com.example.universityeventapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.universityeventapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBrowse = findViewById<Button>(R.id.btnBrowse)

        btnBrowse.setOnClickListener {
            startActivity(Intent(this, EventsListActivity::class.java))
        }
    }
}