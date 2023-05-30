package com.example.nbapp.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "this is a test"
    }
    val text: LiveData<String> = _text
}