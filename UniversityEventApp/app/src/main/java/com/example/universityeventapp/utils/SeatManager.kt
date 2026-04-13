package com.example.universityeventapp.utils

class SeatManager {

    val seats = Array(8) { IntArray(6) { 0 } }

    init {
        // Random 30% booked
        for (i in seats.indices) {
            for (j in seats[i].indices) {
                if ((0..100).random() < 30) {
                    seats[i][j] = 1
                }
            }
        }
    }

    fun toggleSeat(i: Int, j: Int) {
        if (seats[i][j] == 1) return

        seats[i][j] = if (seats[i][j] == 0) 2 else 0
    }
}