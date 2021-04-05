package com.example.myhomeapp.ui.fragments.eventHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventHistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Test"
    }
    val text: LiveData<String> = _text
}