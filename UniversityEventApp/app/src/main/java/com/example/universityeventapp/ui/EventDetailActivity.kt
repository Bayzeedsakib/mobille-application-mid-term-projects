package com.example.universityeventapp.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra("event") as Event

        val title = findViewById<TextView>(R.id.txtTitle)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        title.text = event.title

        btnRegister.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }
}