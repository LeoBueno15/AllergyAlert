package com.example.allergyalert.ui.profiles

import android.content.DialogInterface
import android.content.Intent.getIntent
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.allergyalert.MainActivity
import com.example.allergyalert.R
import com.example.allergyalert.ui.checkproduct.ProductInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProfilesView : AppCompatActivity() {

    lateinit var cancelButton: Button
    lateinit var editButton: Button
    lateinit var deleteButton: Button
    lateinit var profile_data: Array<String>
    lateinit var ref: DatabaseReference
    lateinit var firebaeUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles_view)

        profile_data = intent.getStringArrayExtra("profile data")!!

        for (i in 0..5) {
            if (i == 0) {
                val data: TextView = findViewById(R.id.name_entry) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 1){
                val data: TextView = findViewById(R.id.dob) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 2){
                val data: TextView = findViewById(R.id.height) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 3){
                val data: TextView = findViewById(R.id.weight) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 4){
                val data: TextView = findViewById(R.id.notesText) as TextView
                data.setText(profile_data[i].toString())
            }
            else if (i == 5){
                val data: TextView = findViewById(R.id.allergy_entry) as TextView
                data.setText(profile_data[i].toString())
            }

            editButton = findViewById(R.id.edit_profile)
            cancelButton = findViewById(R.id.cancel_profile)
            deleteButton = findViewById(R.id.delete_profile)

            editButton.setOnClickListener {
                val intent = Intent(this, AddProfile::class.java)
                    .putExtra("profile data", profile_data)

                this.startActivity(intent)
            }

            deleteButton.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("Are you sure?")
                    .setTitle("DELETE PROFILE")
                    .setNegativeButton("Delete", DialogInterface.OnClickListener { dialog, id ->
                        firebaeUser = FirebaseAuth.getInstance().currentUser!!
                        val userId = firebaeUser.uid
                        ref =
                            FirebaseDatabase.getInstance().reference.child(userId).child("profiles")
                        ref.child(profile_data[6]).removeValue()
                        finish()
                    })

                    .setPositiveButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.show()

                val messageView = alert.findViewById<TextView>(android.R.id.message)
                messageView!!.gravity = Gravity.CENTER_VERTICAL
                messageView!!.setTextColor(ContextCompat.getColor(this, R.color.blue))

                val titleView = alert.findViewById<TextView>(android.R.id.title)
                if (titleView != null) {
                    titleView.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
                if (titleView != null) {
                    titleView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
                }

//                titleView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
//                titleView!!.setTextColor(ContextCompat.getColor(this, R.color.white))
            }

            cancelButton.setOnClickListener {
                finish()
            }

        }

    }}

