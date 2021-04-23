package com.example.myhomeapp.ui.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhomeapp.remote.RetrofitApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(
    val api: RetrofitApi
): ViewModel() {
    /*
    private val _text = MutableLiveData<String>().apply {
        value = "Test"
    }
    val text: LiveData<String> = _text

 */
    fun clearHistory() {
        viewModelScope.launch {
                api.clearHistory()
        }
    }
}