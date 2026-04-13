package com.example.contactbookapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val emptyView = findViewById<TextView>(R.id.emptyView)

        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter

        listView.emptyView = emptyView

        // Add Contact
        fab.setOnClickListener {
            showAddDialog()
        }

        // Click
        listView.setOnItemClickListener { _, _, position, _ ->
            val c = adapter.getItem(position)
            Toast.makeText(this, "${c.name}, ${c.phone}, ${c.email}", Toast.LENGTH_SHORT).show()
        }

        // Delete
        listView.setOnItemLongClickListener { _, _, position, _ ->
            val c = adapter.getItem(position)

            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete ${c.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    contactList.remove(c)
                    adapter.updateList(contactList)
                }
                .setNegativeButton("No", null)
                .show()

            true
        }

        // Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String) = false
        })
    }

    private fun showAddDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add, null)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)

        AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString()
                val phone = etPhone.text.toString()
                val email = etEmail.text.toString()

                val contact = Contact(name, phone, email, name.first().toString())
                contactList.add(contact)
                adapter.updateList(contactList)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}