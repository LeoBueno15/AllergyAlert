package com.example.allergyalert.ui.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.example.allergyalert.R

class AddProfile : AppCompatActivity() {

    lateinit var cancelButton: Button
    lateinit var saveButton: Button
    lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile)

        val autotextView = findViewById<AutoCompleteTextView>(R.id.allergy_entry)
        val languages = resources.getStringArray(R.array.days)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, languages)
        autotextView.setAdapter(adapter)

        addButton = findViewById(R.id.add_allergy)
        saveButton = findViewById(R.id.save_profile)
        cancelButton = findViewById(R.id.cancel_profile)

        saveButton.setOnClickListener {
            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}