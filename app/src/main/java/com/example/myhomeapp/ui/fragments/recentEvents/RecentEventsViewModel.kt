package com.example.myhomeapp.ui.fragments.recentEvents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhomeapp.models.Devices
import com.example.myhomeapp.models.Signals
import com.example.myhomeapp.remote.RetrofitApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecentEventsViewModel @Inject constructor(
        val api: RetrofitApi
) : ViewModel() {
    val signalsLiveData = MutableLiveData<List<Signals>>()
    val devicesLiveData = MutableLiveData<List<Devices>>()
    val userId = 1001
    private var timerLeft = 0
    private var timerJob: Job? = null

    fun getDevices() {
        viewModelScope.launch {

            val response = api.getDevices(userId)
            val data = response.data?.data
            data?.let { devices ->
                devicesLiveData.value = devices
            }
        }
    }

    fun getSignals() {
        timerJob?.cancel()
        timerLeft = 1

        timerJob = viewModelScope.launch {
            while (timerLeft > 0) {
                delay(200)
                timerLeft--
                Log.d("timer", timerLeft.toString())
            }

            val response = api.getRecent(userId)
            val data = response.data?.data
            data?.let { signals ->
                signalsLiveData.value = signals
            }
        }
    }

    fun confirmSignal(id: Int){
        viewModelScope.launch { api.confirmSignal(id); }
    }

    sealed class State {
        object SHOW: State()
        object LOADING: State()
    }

    enum class FragmentState{
        SHOW, LOADING
    }
}