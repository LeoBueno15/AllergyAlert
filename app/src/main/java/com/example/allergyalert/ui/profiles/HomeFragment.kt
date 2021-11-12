package com.example.allergyalert.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allergyalert.Profile
import com.example.allergyalert.R
import com.example.allergyalert.databinding.FragmentHomeBinding
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var profileList: ListView
    lateinit var addProfileButton: FloatingActionButton
    lateinit var firebaseAdapter: FirebaseListAdapter<Profile>
    lateinit var ref: DatabaseReference


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val query = FirebaseDatabase.getInstance().reference.child("profiles")

        val firebaseOptions = FirebaseListOptions.Builder<Profile>()
            .setLayout(R.layout.profiles_row)
            .setQuery(query, Profile::class.java)
            .build()

        val adapter = object: FirebaseListAdapter<Profile>(firebaseOptions) {
            override fun populateView(v: View?, model: Profile?, position: Int) {
                val profileNameText = v?.findViewById<TextView>(R.id.row_text)
                profileNameText!!.text = model!!.name
            }
        }

        profileList = binding.profilesListView
//        val profileArray: ArrayList<String> = ArrayList()
//        profileArray.add("Profile 1")
//        profileArray.add("Profile 2")
//        profileArray.add("Profile 3")
//        profileArray.add("Profile 4")
//        profileArray.add("Profile 5")
//
//        val arrayAdapter: ArrayAdapter<String>? = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, profileArray) }
//        profileList.adapter = arrayAdapter

        profileList.adapter = adapter
        profileList.setOnItemClickListener { parent, view, position, id ->
//            var itemName = arrayAdapter?.getItem(position) // The item that was clicked
            var itemName = adapter.getItem(position)
            val intent = Intent(activity, ProfilesView::class.java)
            intent.putExtra("name", itemName.name);
            activity?.startActivity(intent)
        }

        addProfileButton = binding.addProfileButton
        addProfileButton.setOnClickListener {
            val intent = Intent(activity, AddProfile::class.java)
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}