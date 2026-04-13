package com.example.studentregistrationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var etId: EditText
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etAge: EditText

    lateinit var radioGender: RadioGroup

    lateinit var cbFootball: CheckBox
    lateinit var cbCricket: CheckBox
    lateinit var cbBasketball: CheckBox
    lateinit var cbBadminton: CheckBox

    lateinit var spCountry: Spinner

    lateinit var btnDate: Button
    lateinit var btnSubmit: Button
    lateinit var btnReset: Button

    var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etAge = findViewById(R.id.etAge)

        radioGender = findViewById(R.id.radioGender)

        cbFootball = findViewById(R.id.cbFootball)
        cbCricket = findViewById(R.id.cbCricket)
        cbBasketball = findViewById(R.id.cbBasketball)
        cbBadminton = findViewById(R.id.cbBadminton)

        spCountry = findViewById(R.id.spCountry)

        btnDate = findViewById(R.id.btnDate)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        val countries = arrayOf("Bangladesh", "India", "USA", "UK", "Canada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spCountry.adapter = adapter

        btnDate.setOnClickListener {

            val cal = Calendar.getInstance()

            val dp = DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate = "$day/${month + 1}/$year"
                    btnDate.text = selectedDate
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )

            dp.show()
        }

        btnSubmit.setOnClickListener {

            val id = etId.text.toString()
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val age = etAge.text.toString()

            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (age.toInt() <= 0) {
                Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val genderId = radioGender.checkedRadioButtonId

            if (genderId == -1) {
                Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gender = findViewById<RadioButton>(genderId).text.toString()

            val sports = mutableListOf<String>()

            if (cbFootball.isChecked) sports.add("Football")
            if (cbCricket.isChecked) sports.add("Cricket")
            if (cbBasketball.isChecked) sports.add("Basketball")
            if (cbBadminton.isChecked) sports.add("Badminton")

            val sportsText = sports.joinToString(", ")

            val country = spCountry.selectedItem.toString()

            val message = """
                ID: $id
                Name: $name
                Gender: $gender
                Sports: $sportsText
                Country: $country
                DOB: $selectedDate
            """.trimIndent()

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        btnReset.setOnClickListener {

            etId.text.clear()
            etName.text.clear()
            etEmail.text.clear()
            etPassword.text.clear()
            etAge.text.clear()

            radioGender.clearCheck()

            cbFootball.isChecked = false
            cbCricket.isChecked = false
            cbBasketball.isChecked = false
            cbBadminton.isChecked = false

            spCountry.setSelection(0)

            btnDate.text = "Select Date"
            selectedDate = ""
        }
    }
}