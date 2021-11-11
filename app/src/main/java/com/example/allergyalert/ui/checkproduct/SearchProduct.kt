package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allergyalert.MainActivity
import com.example.allergyalert.Product
import com.example.allergyalert.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SearchProduct : AppCompatActivity() {

    lateinit var searchProductBar: EditText
    lateinit var searchProductButton: ImageButton
    lateinit var productView: RecyclerView
    lateinit var listQuery: DatabaseReference
    lateinit var options: FirebaseRecyclerOptions<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)

        listQuery = FirebaseDatabase.getInstance()
            .reference
            .child("")
            .child("products")

        searchProductBar = findViewById(R.id.search_product_bar)
        searchProductButton = findViewById(R.id.search_product_button)
        productView = findViewById(R.id.products_search_view)
        productView.setHasFixedSize(true)
        productView.layoutManager = LinearLayoutManager(this)

        searchProductButton.setOnClickListener {
            firebaseProductSearch()
        }

        options = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(listQuery, Product::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseAdapter = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                return ProductViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_list_layout, parent, false))
            }

            override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int, model: Product) {
                viewHolder.binding(model)
            }
        }

        productView.adapter = firebaseAdapter
    }

    private fun firebaseProductSearch() {
        val firebaseAdapter = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                return ProductViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_list_layout, parent, false))
            }

            override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int, model: Product) {
                viewHolder.binding(model)
            }
        }
        productView.adapter = firebaseAdapter
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(product: Product) {
            val text = itemView.findViewById<TextView>(R.id.product_list_view_text)
            text.text = product.productName
        }
    }
}