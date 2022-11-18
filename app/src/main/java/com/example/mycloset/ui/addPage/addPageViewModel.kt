package com.example.mycloset.ui.addPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class addPageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "What you wearing today?"
    }
    val text: LiveData<String> = _text
}