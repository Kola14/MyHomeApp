package com.example.myhomeapp.ui.fragments.recentEvents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhomeapp.models.Signals
import com.example.myhomeapp.remote.RetrofitApi
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecentEventsViewModel @Inject constructor(
        private val api: RetrofitApi
) : ViewModel() {
    val signalsLiveData = MutableLiveData<List<Signals>>()
    val userId = 1001
    fun getSignals(position: Int) {

        viewModelScope.launch {

            val response = api.getRecent(userId)
            val data = response.data?.data
            data?.let { signals ->
                signalsLiveData.value = signals
            }
        }
    }

    sealed class State {
        object SHOW: State()
        object LOADING: State()
    }

    enum class FragmentState{
        SHOW, LOADING
    }
}