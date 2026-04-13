package com.example.photogalleryapp
import android.os.Bundle
import android.view.View
import android.widget.*
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PhotoAdapter
    private lateinit var photoList: MutableList<Photo>

    private lateinit var selectionToolbar: LinearLayout
    private lateinit var txtSelected: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.gridView)
        selectionToolbar = findViewById(R.id.selectionToolbar)
        txtSelected = findViewById(R.id.txtSelected)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        photoList = generatePhotos()
        adapter = PhotoAdapter(this, photoList)
        gridView.adapter = adapter

        // Click
        gridView.setOnItemClickListener { _, _, position, _ ->
            if (!adapter.isSelectionMode) {
                val photo = adapter.getItem(position) as Photo

                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", photo.resourceId)
                startActivity(intent)
            } else {
                toggleSelection(position)
            }
        }

        // Long press
        gridView.setOnItemLongClickListener { _, _, position, _ ->
            adapter.isSelectionMode = true
            selectionToolbar.visibility = View.VISIBLE
            toggleSelection(position)
            true
        }

        btnDelete.setOnClickListener {
            val count = adapter.getSelectedItems().size
            adapter.removeSelected()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
            exitSelectionMode()
        }

        setupCategoryButtons()
    }

    private fun toggleSelection(position: Int) {
        val photo = adapter.getItem(position) as Photo
        photo.isSelected = !photo.isSelected

        adapter.notifyDataSetChanged()

        val count = adapter.getSelectedItems().size
        txtSelected.text = "$count selected"
    }

    private fun exitSelectionMode() {
        adapter.isSelectionMode = false
        selectionToolbar.visibility = View.GONE
        photoList.forEach { it.isSelected = false }
        adapter.notifyDataSetChanged()
    }

    private fun setupCategoryButtons() {
        findViewById<Button>(R.id.btnAll).setOnClickListener {
            adapter.updateList(photoList)
        }

        findViewById<Button>(R.id.btnNature).setOnClickListener {
            filter("Nature")
        }

        findViewById<Button>(R.id.btnCity).setOnClickListener {
            filter("City")
        }

        findViewById<Button>(R.id.btnAnimals).setOnClickListener {
            filter("Animals")
        }

        findViewById<Button>(R.id.btnFood).setOnClickListener {
            filter("Food")
        }

        findViewById<Button>(R.id.btnTravel).setOnClickListener {
            filter("Travel")
        }
    }

    private fun filter(category: String) {
        val filtered = photoList.filter { it.category == category }.toMutableList()
        adapter.updateList(filtered)
    }

    private fun generatePhotos(): MutableList<Photo> {
        return mutableListOf(
            Photo(1, R.drawable.img1, "Nature 1", "Nature"),
            Photo(2, R.drawable.img2, "City 1", "City"),
            Photo(3, R.drawable.img3, "Animal 1", "Animals"),
            Photo(4, R.drawable.img4, "Food 1", "Food"),
            Photo(5, R.drawable.img5, "Travel 1", "Travel"),
            Photo(6, R.drawable.img6, "Nature 2", "Nature"),
            Photo(7, R.drawable.img7, "City 2", "City"),
            Photo(8, R.drawable.img8, "Animal 2", "Animals"),
            Photo(9, R.drawable.img9, "Food 2", "Food"),
            Photo(10, R.drawable.img10, "Travel 2", "Travel"),
            Photo(11, R.drawable.img11, "Nature 3", "Nature"),
            Photo(12, R.drawable.img12, "City 3", "City")
        )
    }
}