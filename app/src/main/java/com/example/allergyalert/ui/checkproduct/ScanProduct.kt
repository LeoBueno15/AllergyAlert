package com.example.allergyalert.ui.checkproduct

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.allergyalert.MainActivity
import com.example.allergyalert.R
import com.google.zxing.integration.android.IntentIntegrator
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ScanProduct : AppCompatActivity() {

    lateinit var scanButton: Button
    lateinit var homeButton: Button
    private val client = OkHttpClient()
    lateinit var parsed_name: String
    lateinit var parsed_ing: String
    var check = 0


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
                    val url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" +   result.contents + "&pageSize=10&api_key=DEMO_KEY"
                    retrieveWebInfo(url)

                    while (check < 1) {
                        //do nothing, wait for retrieveWebInfo(url) to finish running
                    }
                        val intent = Intent(this, ProductInformation::class.java)

                        intent.putExtra("name", parsed_name)
                        intent.putExtra("ingredients", parsed_ing)
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

    private fun retrieveWebInfo(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val product_info = response.body()?.string()
                val product_properties = product_info?.split("description")
                parsed_name =  product_properties!![1].split('"')[2]

                val ing_group = product_info.split("ingredients")
                parsed_ing =  ing_group[1].split('"')[2]
                check ++

            }
        })

    }



}