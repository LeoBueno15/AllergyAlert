package com.example.allergyalert.ui.profiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.allergyalert.MainActivity
import com.example.allergyalert.Profile
import com.example.allergyalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import kotlin.system.exitProcess

class AddProfile : AppCompatActivity() {

    lateinit var cancelButton: Button
    lateinit var saveButton: Button
    lateinit var profile_data: Array<String>
    lateinit var ref: DatabaseReference
    var hasProfileData: Boolean = false
    lateinit var profileName: TextView
    lateinit var profileDOB: TextView
    lateinit var profileWeight: TextView
    lateinit var profileHeight: TextView
    lateinit var profileNotes: TextView
    lateinit var profileAllergens: TextView
    lateinit var firebaeUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile)

        profileName = findViewById<TextView>(R.id.name_entry)
        profileDOB = findViewById<TextView>(R.id.dob)
        profileWeight = findViewById<TextView>(R.id.weight)
        profileHeight = findViewById<TextView>(R.id.height)
        profileNotes = findViewById<TextView>(R.id.notesText)
        profileAllergens = findViewById<TextView>(R.id.allergy_entry)
        firebaeUser = FirebaseAuth.getInstance().currentUser!!
        val userId = firebaeUser.uid
        ref = FirebaseDatabase.getInstance().reference.child(userId).child("profiles")

        if (intent.getStringArrayExtra("profile data") != null){
            hasProfileData = true
            profile_data = intent.getStringArrayExtra("profile data")!!

            for (i in 0..5) {
                if (i == 0) {
                    profileName.setText(profile_data[i].toString())
                }
                else if (i == 1){
                    profileDOB.setText(profile_data[i].toString())
                }
                else if (i == 2){
                    profileHeight.setText(profile_data[i].toString())
                }
                else if (i == 3){
                    profileWeight.setText(profile_data[i].toString())
                }
                else if (i == 4){
                    profileNotes.setText(profile_data[i].toString())
                }
                else if (i == 5){
                    profileAllergens.setText(profile_data[i].toString())
                }
            }
        } else {
            hasProfileData = false
        }
        saveButton = findViewById(R.id.save_profile)
        cancelButton = findViewById(R.id.cancel_profile)

        saveButton.setOnClickListener {
            if (hasProfileData) {
                val profileId = profile_data.last()
                val profileIdRef = ref.child(profileId)
                profileIdRef.child("name").setValue(profileName.text.toString())
                profileIdRef.child("dob").setValue(profileDOB.text.toString())
                profileIdRef.child("weight").setValue(profileWeight.text.toString())
                profileIdRef.child("height").setValue(profileHeight.text.toString())
                profileIdRef.child("notes").setValue(profileNotes.text.toString())
                profileIdRef.child("allergens").setValue(profileAllergens.text.toString())
            } else {
                val profileKey = ref.push().key
                val profile = Profile(
                    profileKey!!,
                    profileName.text.toString(),
                    profileDOB.text.toString(),
                    profileHeight.text.toString(),
                    profileWeight.text.toString(),
                    profileNotes.text.toString(),
                    profileAllergens.text.toString(),
                    ""
                )
                ref.child(profileKey).setValue(profile)
            }
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
