package com.example.smarteventbooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.smarteventbooking.databinding.ActivityMainBinding
import com.example.smarteventbooking.ui.activities.AuthActivity
import com.example.smarteventbooking.ui.fragments.BookingHistoryFragment
import com.example.smarteventbooking.ui.fragments.EventListFragment
import com.example.smarteventbooking.ui.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Check if user is logged in
        if (!authViewModel.isUserLoggedIn()) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        // Load EventListFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EventListFragment())
                .commit()
        }

        // Set up bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_events -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, EventListFragment())
                        .commit()
                    true
                }
                R.id.nav_bookings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BookingHistoryFragment())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    // TODO: Profile fragment
                    Snackbar.make(binding.root, "Profile coming soon", Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}