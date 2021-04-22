package com.example.myhomeapp.ui.fragments.eventHistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhomeapp.models.Devices
import com.example.myhomeapp.models.Signals
import com.example.myhomeapp.remote.RetrofitApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventHistoryViewModel  @Inject constructor(
     val api: RetrofitApi
) : ViewModel() {
    var signalsLiveData = MutableLiveData<List<Signals>>()
    val devicesLiveData = MutableLiveData<List<Devices>>()
    val userId = 1001

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

        viewModelScope.launch {

            val response = api.getEventHistory(userId)
            val data = response.data?.data
            data?.let { signals ->
                signalsLiveData.value = signals
            }
        }
    }

    fun getSpecificSignals(device_name: String) {
        val specifiedList: ArrayList<Signals> = ArrayList()
        viewModelScope.launch {
            val response = api.getEventHistory(userId)
            val data = response.data?.data
            data?.let { signals ->
                for (i in signals) {
                    if (i.device_name.toString() == device_name){
                        specifiedList.add(i)
                    }
                }
                signalsLiveData.value = specifiedList
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