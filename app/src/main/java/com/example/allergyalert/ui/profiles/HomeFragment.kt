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
import com.example.allergyalert.AddProfile
import com.example.allergyalert.R
import com.example.allergyalert.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var profileList: ListView
    lateinit var addProfileButton: FloatingActionButton


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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        profileList = binding.profilesListView
        val profileArray: ArrayList<String> = ArrayList()
        profileArray.add("Profile 1")
        profileArray.add("Profile 2")
        profileArray.add("Profile 3")
        profileArray.add("Profile 4")
        profileArray.add("Profile 5")

        val arrayAdapter: ArrayAdapter<String>? = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, profileArray) }
        profileList.adapter = arrayAdapter

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