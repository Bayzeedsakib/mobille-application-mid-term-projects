package com.example.universityeventapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.R
import com.example.universityeventapp.adapter.EventAdapter
import com.example.universityeventapp.model.Event

class EventsListActivity : AppCompatActivity() {

    lateinit var adapter: EventAdapter
    lateinit var eventList: List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        // ✅ Use the function here
        eventList = getSampleEvents()

        adapter = EventAdapter(eventList) {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = eventList.filter {
                    it.title.contains(newText ?: "", true)
                }
                adapter.updateList(filtered)
                return true
            }
            override fun onQueryTextSubmit(query: String?) = false
        })
    }

    // ✅ FUNCTION goes INSIDE SAME FILE
    private fun getSampleEvents(): List<Event> {
        return listOf(
            Event(1, "Tech Summit", "10 May", "10:00 AM", "Auditorium", "Tech",
                "Big tech event", 200.0, 48, 30, R.drawable.event1),

            Event(2, "Football Match", "12 May", "4:00 PM", "Playground", "Sports",
                "Inter university match", 100.0, 48, 20, R.drawable.event2),

            Event(3, "Cultural Fest", "15 May", "6:00 PM", "Hall A", "Cultural",
                "Music and dance", 150.0, 48, 25, R.drawable.event3),

            Event(4, "Seminar AI", "18 May", "2:00 PM", "Room 101", "Academic",
                "AI discussion", 120.0, 48, 35, R.drawable.event4),

            Event(5, "Social Meetup", "20 May", "5:00 PM", "Cafeteria", "Social",
                "Networking event", 80.0, 48, 40, R.drawable.event5),

            Event(6, "Hackathon", "25 May", "9:00 AM", "Lab", "Tech",
                "24hr coding", 300.0, 48, 15, R.drawable.event6),

            Event(7, "Cricket Match", "28 May", "3:00 PM", "Ground", "Sports",
                "Friendly match", 100.0, 48, 22, R.drawable.event7),

            Event(8, "Drama Night", "30 May", "7:00 PM", "Auditorium", "Cultural",
                "Stage drama", 180.0, 48, 28, R.drawable.event8)
        )
    }
}