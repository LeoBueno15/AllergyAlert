package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.allergyalert.MainActivity
import com.example.allergyalert.Profile
import com.example.allergyalert.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ProductInformation : AppCompatActivity() {

    lateinit var scanNew: Button
    lateinit var searchNew: Button
    lateinit var homeButton: Button
    lateinit var productNameText: TextView
    lateinit var alertText: TextView
    lateinit var ingredientsText: TextView
    lateinit var allergensDetectedText: TextView
    var alertString: String = "al"
    var detectedString: String = "de"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_information)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        scanNew = findViewById(R.id.scan_new_product_button)
        searchNew = findViewById(R.id.search_new_product_button)
        homeButton = findViewById(R.id.home_button)
        productNameText = findViewById(R.id.product_text)
        alertText = findViewById(R.id.alert_text)
        ingredientsText = findViewById(R.id.ingredients_text)
        ingredientsText.isSelected = true
        allergensDetectedText = findViewById(R.id.allergens_detected_listview)

        val productName = intent.getStringExtra("product").toString()
        val ingredients = intent.getStringExtra("ingredients").toString().trim()
        val alertList = ArrayList<String>()
        val allergensDetectedList = ArrayList<String>()

        val db = FirebaseDatabase.getInstance()
        val profilesRef = db.reference.child("profiles")
        val productsRef = db.reference.child("products")
        profilesRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val profileAllergens = item.getValue(Profile::class.java)?.allergens
                    val profileName = item.getValue(Profile::class.java)!!.name
                    val allergenList = profileAllergens?.split(", ")
                    if (allergenList != null) {
                        for (allergen in allergenList) {
                            if (ingredients.lowercase(Locale.getDefault()).contains(allergen.lowercase(Locale.getDefault()))) {
                                if (!alertList.contains(profileName)) {
                                    alertList.add(profileName)
                                }
                                if (!allergensDetectedList.contains(allergen)) {
                                    allergensDetectedList.add(allergen)
                                }
                            }
                        }
                    }
                }

                if (alertList.isNotEmpty()) {
                    alertString = "Allergens detected! Affected users: "
                    alertText.setTextColor(Color.parseColor("#ffff0000"))
                } else {
                    alertString = "No allergens detected  "
                }
                for (prof in alertList) {
                    alertString = alertString + prof + ", "
                }

                if (allergensDetectedList.isNotEmpty()) {
                    detectedString = ""
                } else {
                    detectedString = "None  "
                }
                for (allergen in allergensDetectedList) {
                    detectedString = detectedString + allergen + ", "
                }

                alertString = alertString.substring(0, alertString.length - 2)
                detectedString = detectedString.substring(0, detectedString.length - 2)

                alertText.text = alertString
                allergensDetectedText.text = detectedString
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        alertText.text = alertString
        allergensDetectedText.text = detectedString
        productNameText.text = productName
        ingredientsText.text = ingredients

        scanNew.setOnClickListener {
            val intent = Intent(this, ScanProduct::class.java)
            this.startActivity(intent)
        }

        searchNew.setOnClickListener{
            val intent = Intent(this, SearchProduct::class.java)
            this.startActivity(intent)
        }

        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}