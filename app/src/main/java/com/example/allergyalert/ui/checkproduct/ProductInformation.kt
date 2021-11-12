package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.allergyalert.MainActivity
import com.example.allergyalert.R

class ProductInformation : AppCompatActivity() {

    lateinit var scanNew: Button
    lateinit var searchNew: Button
    lateinit var homeButton: Button
    lateinit var productNameText: TextView
    lateinit var alertText: TextView
    lateinit var ingredientsText: TextView
    lateinit var allergensDetectedText: ListView

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

        productNameText.text = intent.getStringExtra("product").toString()
        ingredientsText.text = intent.getStringExtra("ingredients").toString().trim()
        println(ingredientsText.text)

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