package com.example.allergyalert.ui.checkproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckProductViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text
}