package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.allergyalert.MainActivity
import com.example.allergyalert.Profile
import com.example.allergyalert.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    lateinit var productInfoLayout: LinearLayout
    lateinit var prodInfoNotFound: TextView
    var alertString: String = ""
    var detectedString: String = ""
    lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_information)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        productInfoLayout = findViewById(R.id.product_info_layout)
        scanNew = findViewById(R.id.scan_new_product_button)
        searchNew = findViewById(R.id.search_new_product_button)
        homeButton = findViewById(R.id.home_button)
        productNameText = findViewById(R.id.product_text)
        alertText = findViewById(R.id.alert_text)
        ingredientsText = findViewById(R.id.ingredients_text)
        prodInfoNotFound = findViewById(R.id.prod_not_found)
        ingredientsText.isSelected = true
        allergensDetectedText = findViewById(R.id.allergens_detected_listview)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val userId = firebaseUser.uid

        val productName = intent.getStringExtra("name").toString()
        val ingredients = intent.getStringExtra("ingredients").toString().trim()

        if (productName == "no name" || ingredients == "no ingredients") {
            productInfoLayout.visibility = View.INVISIBLE
            prodInfoNotFound.visibility = View.VISIBLE
        }

        val alertList = ArrayList<String>()
        val allergensDetectedList = ArrayList<String>()

        val db = FirebaseDatabase.getInstance()
        val profilesRef = db.reference.child(userId).child("profiles")
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
                                if (!allergensDetectedList.contains(allergen.lowercase(Locale.getDefault()))) {
                                    allergensDetectedList.add(allergen.lowercase(Locale.getDefault()))
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
            finish()
        }

        searchNew.setOnClickListener{
            val intent = Intent(this, SearchProduct::class.java)
            this.startActivity(intent)
            finish()
        }

        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish()
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