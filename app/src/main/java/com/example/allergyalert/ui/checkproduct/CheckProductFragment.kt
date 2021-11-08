package com.example.allergyalert.ui.checkproduct

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.allergyalert.R
import com.example.allergyalert.databinding.FragmentCheckProductBinding

class CheckProductFragment : Fragment() {

    private lateinit var checkProductViewModel: CheckProductViewModel
    private var _binding: FragmentCheckProductBinding? = null
    lateinit var scanButton: Button
    lateinit var searchButton: Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_check_product, container, false)

        checkProductViewModel =
            ViewModelProvider(this).get(CheckProductViewModel::class.java)

        _binding = FragmentCheckProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        checkProductViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        scanButton = binding.scanProduct
        searchButton = binding.searchProduct

        scanButton.setOnClickListener {
            val intent = Intent(activity, ScanProduct::class.java)
            activity?.startActivity(intent)
        }

        searchButton.setOnClickListener {
            val intent = Intent(activity, SearchProduct::class.java)
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}