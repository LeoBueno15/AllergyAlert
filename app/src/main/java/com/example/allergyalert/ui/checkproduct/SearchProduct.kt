package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import com.example.allergyalert.MainActivity
import com.example.allergyalert.R

class SearchProduct : AppCompatActivity() {

    lateinit var searchProduct: SearchView
    lateinit var searchOutput: ListView
    lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchProduct = findViewById(R.id.product_search_bar)
        searchOutput = findViewById(R.id.search_output)
        homeButton = findViewById(R.id.home_button)

        val sampleOutput = arrayOf("item1", "item2", "item3", "item4", "item5")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, sampleOutput)

        searchOutput.adapter = adapter

        searchProduct.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchProduct.clearFocus()
                if (sampleOutput.contains(query)) {
                    adapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        searchOutput.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemText = parent.getItemAtPosition(position)
            val intent = Intent(this, ProductInformation::class.java)
            this.startActivity(intent.putExtra("selected_product", selectedItemText.toString()))
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