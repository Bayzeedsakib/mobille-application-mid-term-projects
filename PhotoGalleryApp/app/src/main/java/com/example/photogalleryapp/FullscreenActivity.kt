package com.example.photogalleryapp
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Matrix
import android.view.ScaleGestureDetector

class FullscreenActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val matrix = Matrix()
    private var scale = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        imageView = findViewById(R.id.fullImage)

        val resId = intent.getIntExtra("image", 0)

        if (resId != 0) {
            imageView.setImageResource(resId)
        }

        val scaleGestureDetector = ScaleGestureDetector(this,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    scale *= detector.scaleFactor
                    scale = Math.max(1f, Math.min(scale, 5f))

                    matrix.setScale(scale, scale,
                        detector.focusX, detector.focusY)

                    imageView.imageMatrix = matrix
                    return true
                }
            })

        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }
}