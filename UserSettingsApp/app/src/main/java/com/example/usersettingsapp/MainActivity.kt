package com.university.usersettings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var radioGroupTheme: RadioGroup
    private lateinit var rbLight: RadioButton
    private lateinit var rbDark: RadioButton
    private lateinit var rbSystem: RadioButton
    private lateinit var switchNotifications: SwitchCompat
    private lateinit var spinnerLanguage: Spinner
    private lateinit var seekBarFont: SeekBar
    private lateinit var txtFontSize: TextView

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREF_NAME = "AppSettings"

        const val KEY_THEME = "KEY_THEME"
        const val KEY_NOTIFICATIONS = "KEY_NOTIFICATIONS"
        const val KEY_LANGUAGE = "KEY_LANGUAGE"
        const val KEY_FONT_SIZE = "KEY_FONT_SIZE"
        const val KEY_LAST_SAVED = "KEY_LAST_SAVED"
        const val KEY_STUDENT_NAME = "KEY_STUDENT_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "User Settings"

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        etStudentName = findViewById(R.id.etStudentName)
        radioGroupTheme = findViewById(R.id.radioGroupTheme)
        rbLight = findViewById(R.id.rbLight)
        rbDark = findViewById(R.id.rbDark)
        rbSystem = findViewById(R.id.rbSystem)
        switchNotifications = findViewById(R.id.switchNotifications)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        seekBarFont = findViewById(R.id.seekBarFont)
        txtFontSize = findViewById(R.id.txtFontSize)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnViewSettings = findViewById<Button>(R.id.btnViewSettings)
        val fabProfile = findViewById<FloatingActionButton>(R.id.fabProfile)

        val languages = arrayOf("English", "Bangla", "Arabic", "French")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languages
        )

        spinnerLanguage.adapter = adapter

        seekBarFont.max = 12

        seekBarFont.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val fontSize = progress + 12
                txtFontSize.text = "Font Size: ${fontSize}sp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSave.setOnClickListener {
            saveSettings()
        }

        btnReset.setOnClickListener {
            resetSettings()
        }

        btnViewSettings.setOnClickListener {
            startActivity(
                Intent(this, SettingsViewerActivity::class.java)
            )
        }

        fabProfile.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        restoreSettings()
    }

    private fun saveSettings() {

        val theme = when (radioGroupTheme.checkedRadioButtonId) {
            R.id.rbLight -> "Light"
            R.id.rbDark -> "Dark"
            else -> "System Default"
        }

        val editor = sharedPreferences.edit()

        editor.putString(KEY_STUDENT_NAME,
            etStudentName.text.toString())

        editor.putString(KEY_THEME, theme)

        editor.putBoolean(
            KEY_NOTIFICATIONS,
            switchNotifications.isChecked
        )

        editor.putString(
            KEY_LANGUAGE,
            spinnerLanguage.selectedItem.toString()
        )

        editor.putInt(
            KEY_FONT_SIZE,
            seekBarFont.progress + 12
        )

        editor.putLong(
            KEY_LAST_SAVED,
            System.currentTimeMillis()
        )

        editor.apply()

        Toast.makeText(
            this,
            "Settings Saved",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun restoreSettings() {

        etStudentName.setText(
            sharedPreferences.getString(KEY_STUDENT_NAME, "")
        )

        when (sharedPreferences.getString(
            KEY_THEME,
            "System Default")
        ) {
            "Light" -> rbLight.isChecked = true
            "Dark" -> rbDark.isChecked = true
            else -> rbSystem.isChecked = true
        }

        switchNotifications.isChecked =
            sharedPreferences.getBoolean(
                KEY_NOTIFICATIONS,
                false
            )

        val language =
            sharedPreferences.getString(KEY_LANGUAGE,
                "English")

        val position = (spinnerLanguage.adapter as ArrayAdapter<String>)
            .getPosition(language)

        spinnerLanguage.setSelection(position)

        val fontSize =
            sharedPreferences.getInt(KEY_FONT_SIZE, 16)

        seekBarFont.progress = fontSize - 12

        txtFontSize.text = "Font Size: ${fontSize}sp"
    }

    private fun resetSettings() {

        etStudentName.setText("")

        rbSystem.isChecked = true

        switchNotifications.isChecked = false

        spinnerLanguage.setSelection(0)

        seekBarFont.progress = 4

        txtFontSize.text = "Font Size: 16sp"

        sharedPreferences.edit().clear().apply()

        Toast.makeText(
            this,
            "Settings Reset",
            Toast.LENGTH_SHORT
        ).show()
    }
}