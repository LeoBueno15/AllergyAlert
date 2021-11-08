package com.example.allergyalert.ui.profiles

import android.content.Intent.getIntent
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.allergyalert.R


class ProfilesView : AppCompatActivity() {

    lateinit var cancelButton: Button
    lateinit var saveButton: Button
    lateinit var profile_data: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles_view)

        val ss: String = intent.getStringExtra("name").toString()
        val res: Resources = resources
        val profile_data = res.getStringArray(R.array.test_profile)


        for (i in 0..5) {
            if (i == 0) {
                val data: TextView = findViewById(R.id.name_entry) as TextView
                println("name is " + profile_data[i].toString())
                data.setText(profile_data[i].toString())
            }
            else if (i == 1){
                val data: TextView = findViewById(R.id.dob) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 2){
                val data: TextView = findViewById(R.id.height) as TextView
                data.setText(profile_data[i].toString() + "cm")
            }
            else if (i == 3){
                val data: TextView = findViewById(R.id.weight) as TextView
                data.setText(profile_data[i].toString() + "kg")
            }
            else if (i == 4){
                val data: TextView = findViewById(R.id.notesText) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 5){
                val data: TextView = findViewById(R.id.allergy_entry) as TextView
                data.setText(profile_data[i].toString())
            }

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
}
