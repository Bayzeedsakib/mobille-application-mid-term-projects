package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)

        val btnTop = findViewById<Button>(R.id.btnTop)
        val btnBookmark = findViewById<ImageButton>(R.id.btnBookmark)
        val btnShare = findViewById<ImageButton>(R.id.btnShare)

        val sectionIntro = findViewById<TextView>(R.id.sectionIntro)
        val sectionKey = findViewById<TextView>(R.id.sectionKey)
        val sectionAnalysis = findViewById<TextView>(R.id.sectionAnalysis)
        val sectionConclusion = findViewById<TextView>(R.id.sectionConclusion)

        findViewById<Button>(R.id.btnIntro).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, sectionIntro.top)
            }
        }

        findViewById<Button>(R.id.btnKey).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, sectionKey.top)
            }
        }

        findViewById<Button>(R.id.btnAnalysis).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, sectionAnalysis.top)
            }
        }

        findViewById<Button>(R.id.btnConclusion).setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, sectionConclusion.top)
            }
        }

        btnBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Check out this article!")
            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}