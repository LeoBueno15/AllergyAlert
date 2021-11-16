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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class SearchProduct : AppCompatActivity(), ProductAdapter.OnProductItemClickListener {

    lateinit var ref: DatabaseReference
    lateinit var list: ArrayList<Product>
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)

        list = ArrayList()

        ref = FirebaseDatabase.getInstance().reference.child("products")
        recyclerView = findViewById(R.id.products_search_view)
        searchView = findViewById(R.id.search_product_bar)
        homeButton = findViewById(R.id.home_button)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (ref != null) {
            ref.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        for (ds: DataSnapshot in snapshot.children) {
                            ds.getValue(Product::class.java)?.let { list.add(it) }
                        }
                        val adapter: ProductAdapter = ProductAdapter(list, this@SearchProduct)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        search(newText)
                    }
                    return true
                }

            })
        }
    }

    fun search(string: String){
        val searchList = ArrayList<Product>()
        for (item: Product in list) {
            if (item.productName.lowercase(Locale.getDefault()).contains(string.lowercase(Locale.getDefault()))) {
                searchList.add(item)
            }
        }
        val productAdapter = ProductAdapter(searchList, this)
        recyclerView.adapter = productAdapter
    }

    override fun onItemClick(item: Product, position: Int) {
        val intent = Intent(this, ProductInformation::class.java).putExtra("name", item.productName).putExtra("ingredients", item.prodIngredients)
        this.startActivity(intent)
    }
}

class ProductAdapter(var list: ArrayList<Product>, var clickListener: OnProductItemClickListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var productName: TextView = itemView.findViewById(R.id.product_list_view_text)
        fun initialize(item: Product, action: OnProductItemClickListener) {
            productName.text = item.productName

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.productName.text = list[position].productName
        holder.initialize(list[position], clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnProductItemClickListener {
        fun onItemClick(item: Product, position: Int)
    }
}