package com.example.universityeventapp.ui

class BookingActivity : AppCompatActivity() {

    lateinit var seatManager: SeatManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val table = findViewById<TableLayout>(R.id.tableLayout)

        seatManager = SeatManager()

        for (i in 0 until 8) {
            val row = TableRow(this)

            for (j in 0 until 6) {
                val seat = TextView(this)
                seat.text = "${i+1}-${j+1}"
                seat.setPadding(16,16,16,16)

                updateSeatColor(seat, i, j)

                seat.setOnClickListener {
                    seatManager.toggleSeat(i, j)
                    updateSeatColor(seat, i, j)
                }

                row.addView(seat)
            }
            table.addView(row)
        }
    }

    fun updateSeatColor(view: TextView, i: Int, j: Int) {
        when(seatManager.seats[i][j]) {
            0 -> view.setBackgroundColor(Color.GREEN)
            1 -> view.setBackgroundColor(Color.RED)
            2 -> view.setBackgroundColor(Color.BLUE)
        }
    }
}