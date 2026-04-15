package com.example.universityeventapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.R
import com.example.universityeventapp.adapter.GalleryAdapter
import com.example.universityeventapp.model.Event
import com.example.universityeventapp.adapter.SpeakerAdapter
import com.example.universityeventapp.model.Speaker
class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra("event") as? Event

        val title = findViewById<TextView>(R.id.txtTitle)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        title.text = event?.title ?: "Event"

        btnRegister.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }

        val galleryRecycler = findViewById<RecyclerView>(R.id.galleryRecycler)

        val images = listOf(
            R.drawable.event1,
            R.drawable.event2,
            R.drawable.event3
        )

        val adapter = GalleryAdapter(images)

        galleryRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        galleryRecycler.adapter = adapter

        val speakerRecycler = findViewById<RecyclerView>(R.id.speakerRecycler)

        val speakers = listOf(
            Speaker("John Doe", "Professor", R.drawable.event1),
            Speaker("Alice Smith", "Software Engineer", R.drawable.event2),
            Speaker("Rahim Khan", "Data Scientist", R.drawable.event3)
        )

        val speakerAdapter = SpeakerAdapter(speakers)

        speakerRecycler.layoutManager = LinearLayoutManager(this)
        speakerRecycler.adapter = speakerAdapter
    }
}