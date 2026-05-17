package com.example.universityeventapp.ui

import androidx.activity.OnBackPressedCallback
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.universityeventapp.R
import com.example.universityeventapp.model.Event
import com.example.universityeventapp.utils.SeatManager

class BookingActivity : AppCompatActivity() {

    lateinit var seatManager: SeatManager

    lateinit var txtSummary: TextView
    lateinit var btnConfirm: Button

    var selectedCount = 0
    var pricePerSeat = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val table = findViewById<TableLayout>(R.id.tableLayout)
        txtSummary = findViewById(R.id.txtSummary)
        btnConfirm = findViewById(R.id.btnConfirm)

        // 🔹 Get event price dynamically
        val event = intent.getSerializableExtra("event") as? Event
        pricePerSeat = event?.price?.toInt() ?: 100

        seatManager = SeatManager()

        for (i in 0 until 8) {
            val row = TableRow(this)

            for (j in 0 until 6) {
                val seat = TextView(this)
                seat.text = "${i + 1}-${j + 1}"
                seat.setPadding(16, 16, 16, 16)

                updateSeatColor(seat, i, j)

                seat.setOnClickListener {

                    if (seatManager.seats[i][j] == 1) return@setOnClickListener // booked seat

                    if (seatManager.seats[i][j] == 0) {
                        seatManager.seats[i][j] = 2
                        selectedCount++
                    } else if (seatManager.seats[i][j] == 2) {
                        seatManager.seats[i][j] = 0
                        selectedCount--
                    }

                    updateSeatColor(seat, i, j)
                    updateSummary()
                }

                row.addView(seat)
            }
            table.addView(row)
        }

        // 🔹 Confirm Booking Button
        btnConfirm.setOnClickListener {

            if (selectedCount == 0) {
                Toast.makeText(this, "Select at least one seat", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Confirm Booking")
                .setMessage("Book $selectedCount seats?")
                .setPositiveButton("Confirm") { _, _ ->
                    Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        updateSummary()

        // 🔹 Modern Back Press Handling
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    if (selectedCount > 0) {
                        AlertDialog.Builder(this@BookingActivity)
                            .setTitle("Exit?")
                            .setMessage("You have selected seats. Leave?")
                            .setPositiveButton("Yes") { _, _ ->
                                finish()
                            }
                            .setNegativeButton("No", null)
                            .show()
                    } else {
                        finish()
                    }
                }
            }
        )
    }

    private fun updateSeatColor(view: TextView, i: Int, j: Int) {
        when (seatManager.seats[i][j]) {
            0 -> view.setBackgroundColor(Color.GREEN)
            1 -> view.setBackgroundColor(Color.RED)
            2 -> view.setBackgroundColor(Color.BLUE)
        }
    }

    private fun updateSummary() {
        val total = selectedCount * pricePerSeat
        txtSummary.text = "$selectedCount seats selected | Total: $total BDT"
    }
}