package com.example.loginprofileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var loginBtn: Button
    lateinit var logoutBtn: Button
    lateinit var forgot: TextView
    lateinit var progress: ProgressBar
    lateinit var profileCard: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        logoutBtn = findViewById(R.id.logoutBtn)
        forgot = findViewById(R.id.forgot)
        progress = findViewById(R.id.progress)
        profileCard = findViewById(R.id.profileCard)

        // LOGIN BUTTON
        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user == "admin" && pass == "1234") {
                progress.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    progress.visibility = View.GONE
                    showProfile()
                }, 2000)

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // LOGOUT BUTTON
        logoutBtn.setOnClickListener {
            profileCard.visibility = View.GONE
            showLoginForm()

            username.text.clear()
            password.text.clear()
        }

        // FORGOT PASSWORD
        forgot.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProfile() {
        profileCard.visibility = View.VISIBLE

        username.visibility = View.GONE
        password.visibility = View.GONE
        loginBtn.visibility = View.GONE
        forgot.visibility = View.GONE
    }

    private fun showLoginForm() {
        username.visibility = View.VISIBLE
        password.visibility = View.VISIBLE
        loginBtn.visibility = View.VISIBLE
        forgot.visibility = View.VISIBLE
    }
}