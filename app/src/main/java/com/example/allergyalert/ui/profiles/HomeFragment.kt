package com.example.allergyalert.ui.profiles

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allergyalert.AccountSignIn
import com.example.allergyalert.Profile
import com.example.allergyalert.R
import com.example.allergyalert.databinding.FragmentHomeBinding
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var profileList: ListView
    lateinit var addProfileButton: FloatingActionButton
    lateinit var firebaseAdapter: FirebaseListAdapter<Profile>
    lateinit var ref: DatabaseReference
    lateinit var profile_data: Array<String>
    lateinit var logoutButton: Button
    lateinit var firebaeUser: FirebaseUser
    lateinit var logOutButton: ImageButton

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        firebaeUser = FirebaseAuth.getInstance().currentUser!!
        val userId = firebaeUser.uid
        val query = FirebaseDatabase.getInstance().reference.child(userId).child("profiles")

        val firebaseOptions = FirebaseListOptions.Builder<Profile>()
            .setLayout(R.layout.profiles_row)
            .setQuery(query, Profile::class.java)
            .setLifecycleOwner(this)
            .build()

        firebaseAdapter = object: FirebaseListAdapter<Profile>(firebaseOptions) {
            override fun populateView(v: View?, model: Profile?, position: Int) {
                val profileNameText = v?.findViewById<TextView>(R.id.row_text)
                profileNameText!!.text = model!!.name
            }
        }

        profileList = binding.profilesListView
        logoutButton = binding.logoutButton

        profileList.adapter = firebaseAdapter
        profileList.setOnItemClickListener { parent, view, position, id ->
//            var itemName = arrayAdapter?.getItem(position) // The item that was clicked
            val itemName = firebaseAdapter.getItem(position)
            val intent = Intent(activity, ProfilesView::class.java)
            profile_data = Array(7) {"default"}

            profile_data[0] = itemName.name
            profile_data[1] = itemName.dob
            profile_data[2] = itemName.height
            profile_data[3] = itemName.weight
            profile_data[4] = itemName.notes
            profile_data[5] = itemName.allergens
            profile_data[6] = itemName.id

            intent.putExtra("profile data", profile_data);
            activity?.startActivity(intent)
        }

        addProfileButton = binding.addProfileButton
        addProfileButton.setOnClickListener {
            val intent = Intent(activity, AddProfile::class.java)
            activity?.startActivity(intent)
        }

        logoutButton.setOnClickListener {
            val dialog: AlertDialog
            val builder = AlertDialog.Builder(activity)

            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to logout?")

            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> dialogFun(1)
                    DialogInterface.BUTTON_NEGATIVE -> dialogFun(0)
                }
            }

            builder.setPositiveButton("YES",dialogClickListener)
            builder.setNegativeButton("NO",dialogClickListener)
            dialog = builder.create()
            dialog.show()
        }

        return root
    }

    fun dialogFun(operation: Int) {
        if (operation == 1) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, AccountSignIn::class.java))
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        firebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        firebaseAdapter.stopListening()
    }
}