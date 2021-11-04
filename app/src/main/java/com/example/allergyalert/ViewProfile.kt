package com.example.allergyalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ViewProfile : AppCompatActivity() {
    class AddProfile : AppCompatActivity() {

        lateinit var cancelButton: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_view_profile)

            cancelButton = findViewById(R.id.cancel_profile)

            cancelButton.setOnClickListener {
                finish()
            }
        }
    }
}