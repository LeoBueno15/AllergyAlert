package com.example.allergyalert.ui.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.allergyalert.R

class AddProfile : AppCompatActivity() {

    lateinit var cancelButton: Button
    lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile)

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