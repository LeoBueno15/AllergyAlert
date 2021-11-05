package com.example.allergyalert

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.allergyalert.ui.checkproduct.CheckProductFragment
import com.google.zxing.integration.android.IntentIntegrator

class ScanProduct : AppCompatActivity() {

    lateinit var scanButton: Button
    lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_product)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        scanButton = findViewById(R.id.scan_button)
        homeButton = findViewById(R.id.home_button)

        scanButton.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned:" + result.contents, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ProductInformation::class.java)
                    this.startActivity(intent)
                }
            } else {
                super. onActivityResult(requestCode, resultCode, data)
            }
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