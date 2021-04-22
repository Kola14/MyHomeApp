package com.example.myhomeapp.ui.fragments.eventHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import com.example.myhomeapp.models.Devices
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventHistoryFragment : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: EventHistoryViewModel
    lateinit var eventHistoryAdapter: EventHistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(EventHistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        observeLiveData()

        viewModel.getSignals(0)
    }

    private fun initAdapters() {
        eventHistoryAdapter = EventHistoryAdapter(viewModel.getSignals(0))
        view?.findViewById<RecyclerView>(R.id.recentEventRecycler)?.apply {
            adapter = eventHistoryAdapter
        }

        val deviceList: ArrayList<String> = ArrayList()
        deviceList.add("All")
        var devicesData = ArrayList<Devices>()
        lifecycleScope.launch {

            val response = viewModel.api.getDevices(1101)
            val data = response.data?.data
            data?.let { devices ->
                devicesData = devices as ArrayList<Devices>
            }

            for (i in devicesData){
                deviceList.add(i.device_id.toString());
            }

            view?.findViewById<Spinner>(R.id.deviceSelectSpinner)?.apply {
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, deviceList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.adapter =  spinnerAdapter
            }
        }

    }

    private fun observeLiveData() {
        viewModel.signalsLiveData.observe(viewLifecycleOwner) { newsList ->
            if (!newsList.isNullOrEmpty()) {
                eventHistoryAdapter.submit(newsList)
            }
        }
    }
}