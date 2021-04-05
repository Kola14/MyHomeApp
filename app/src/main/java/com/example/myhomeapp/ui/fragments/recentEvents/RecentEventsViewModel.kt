package com.example.myhomeapp.ui.fragments.recentEvents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecentEventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "test"
    }
    val text: LiveData<String> = _text
}